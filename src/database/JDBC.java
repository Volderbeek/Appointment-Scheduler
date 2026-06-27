package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Static class used to interface with JDBC.
 */
public abstract class JDBC {

    private static final String dbPath = helper.Env.get("DB_PATH", "client_schedule.db");
    private static final String jdbcUrl = "jdbc:sqlite:" + dbPath;
    private static final String driver = "org.sqlite.JDBC";
    private static Connection connection;

    /**
     * Gets a reference to the established connection.
     * 
     * @return the Connection object created with openConnection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Opens the connection to the database with the pre-defined information.
     */
    public static void openConnection() {
        try {
            // Resilient dynamic class loading for SQLite driver if not on classpath
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                System.out.println("SQLite driver not found on standard classpath. Attempting dynamic load from lib/...");
                File libDir = new File("lib");
                if (libDir.exists() && libDir.isDirectory()) {
                    File[] jars = libDir.listFiles((dir, name) -> name.endsWith(".jar"));
                    if (jars != null && jars.length > 0) {
                        URL[] urls = new URL[jars.length];
                        for (int i = 0; i < jars.length; i++) {
                            urls[i] = jars[i].toURI().toURL();
                        }
                        URLClassLoader loader = new URLClassLoader(urls, JDBC.class.getClassLoader());
                        Class<?> driverClass = Class.forName(driver, true, loader);
                        Driver driverInstance = (Driver) driverClass.getDeclaredConstructor().newInstance();
                        DriverManager.registerDriver(new DelegatingDriver(driverInstance));
                        System.out.println("SQLite driver dynamically registered!");
                    } else {
                        throw new RuntimeException("No libraries found in lib/ directory to load dynamically.");
                    }
                } else {
                    throw new RuntimeException("lib/ directory not found for dynamic library loading.");
                }
            }

            File dbFile = new File(dbPath);
            boolean dbExists = dbFile.exists() && dbFile.length() > 0;

            connection = DriverManager.getConnection(jdbcUrl);
            
            // Enable foreign key support in SQLite
            try (Statement pragmaStmt = connection.createStatement()) {
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }
            
            System.out.println("Connection successful!");

            if (!dbExists) {
                initializeDatabase();
            }
        } catch (Exception exception) {
            System.err.println("Fatal Error during database connection: " + exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Failed to establish database connection", exception);
        }
    }

    /**
     * Initializes the SQLite database using the script in resources.
     */
    private static void initializeDatabase() {
        System.out.println("Initializing new SQLite database...");
        try (InputStream inputStream = JDBC.class.getResourceAsStream("/init_sqlite.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     inputStream != null ? inputStream : new FileInputStream("resources/init_sqlite.sql"), StandardCharsets.UTF_8))) {
            
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("--") || trimmed.isEmpty()) {
                        continue;
                    }
                    sqlBuilder.append(line).append("\n");
                    if (trimmed.endsWith(";")) {
                        String query = sqlBuilder.toString().trim();
                        if (!query.isEmpty()) {
                            statement.addBatch(query);
                        }
                        sqlBuilder.setLength(0);
                    }
                }
                statement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
                System.out.println("Database initialized successfully!");
            } catch (Exception stmtEx) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw stmtEx;
            }
        } catch (Exception e) {
            System.err.println("Error initializing SQLite database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed!");
            }
        } catch (Exception exception) {
            System.out.println("Error:" + exception.getMessage());
        }
    }

    /**
     * Helper to parse any SQLite datetime string into a LocalDateTime.
     * Supports formats like "YYYY-MM-DD HH:MM:SS", "YYYY-MM-DDTHH:MM", "YYYY-MM-DD HH:MM:SS.SSS", etc.
     */
    public static java.time.LocalDateTime parseLocalDateTime(String str) {
        if (str == null || str.trim().isEmpty()) return null;
        String isoStr = str.trim().replace(' ', 'T');
        if (isoStr.length() == 16) {
            isoStr += ":00";
        }
        return java.time.LocalDateTime.parse(isoStr);
    }

}

/**
 * Driver wrapper to delegate requests to a dynamically loaded JDBC driver.
 */
class DelegatingDriver implements Driver {
    private final Driver driver;
    public DelegatingDriver(Driver driver) {
        this.driver = driver;
    }
    @Override
    public Connection connect(String url, java.util.Properties info) throws java.sql.SQLException {
        return driver.connect(url, info);
    }
    @Override
    public boolean acceptsURL(String url) throws java.sql.SQLException {
        return driver.acceptsURL(url);
    }
    @Override
    public java.sql.DriverPropertyInfo[] getPropertyInfo(String url, java.util.Properties info) throws java.sql.SQLException {
        return driver.getPropertyInfo(url, info);
    }
    @Override
    public int getMajorVersion() {
        return driver.getMajorVersion();
    }
    @Override
    public int getMinorVersion() {
        return driver.getMinorVersion();
    }
    @Override
    public boolean jdbcCompliant() {
        return driver.jdbcCompliant();
    }
    @Override
    public java.util.logging.Logger getParentLogger() throws java.sql.SQLFeatureNotSupportedException {
        return driver.getParentLogger();
    }
}

