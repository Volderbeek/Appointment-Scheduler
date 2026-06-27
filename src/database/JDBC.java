package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Static class used to interface with JDBC.
 */
public abstract class JDBC {

    private static final String host = helper.Env.get("DB_HOST", "//localhost/");
    private static final String port = helper.Env.get("DB_PORT", "3306");
    private static final String databaseName = helper.Env.get("DB_NAME", "client_schedule");
    private static final String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName
            + "?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = helper.Env.get("DB_USER", "sqlUser");
    private static final String password = helper.Env.get("DB_PASSWORD", "Passw0rd!");
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
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        } catch (Exception exception) {
            System.out.println("Error:" + exception.getMessage());
        }
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception exception) {
            System.out.println("Error:" + exception.getMessage());
        }
    }

}
