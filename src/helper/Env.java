package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight helper to load variables from a .env file and fallback to system environment variables.
 */
public class Env {
    private static final Map<String, String> envMap = new HashMap<>();

    static {
        load();
    }

    /**
     * Loads the .env file if it exists.
     */
    public static void load() {
        File file = new File(".env");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    int eqIdx = line.indexOf('=');
                    if (eqIdx > 0) {
                        String key = line.substring(0, eqIdx).trim();
                        String value = line.substring(eqIdx + 1).trim();
                        // Handle potential surrounding quotes (double or single)
                        if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
                            value = value.substring(1, value.length() - 1);
                        } else if (value.startsWith("'") && value.endsWith("'") && value.length() >= 2) {
                            value = value.substring(1, value.length() - 1);
                        }
                        envMap.put(key, value);
                    }
                }
            } catch (IOException e) {
                System.err.println("Warning: Error reading .env file: " + e.getMessage());
            }
        }
    }

    /**
     * Retrieves the value of the environment variable by key.
     * @param key the name of the variable
     * @return the value, or null if not found
     */
    public static String get(String key) {
        String val = envMap.get(key);
        if (val != null) {
            return val;
        }
        return System.getenv(key);
    }

    /**
     * Retrieves the value of the environment variable by key, falling back to a default value if not found.
     * @param key the name of the variable
     * @param defaultValue the default value to return if the key is not defined
     * @return the value, or the default value
     */
    public static String get(String key, String defaultValue) {
        String val = get(key);
        return val != null ? val : defaultValue;
    }
}
