package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Static class used to model the logged-in user.
 */
public abstract class User {

    private static int userId;
    private static String userName;

    /**
     * Logs the user in by setting the username and password.
     * @param id the user ID
     * @param user the username
     */
    public static void login(int id, String user) {
        userId = id;
        userName = user;
    }

    /**
     * Logs the user out by clearing user information and saving logout history to a file.
     * @throws IOException throw file I/O errors back to the JVM
     */
    public static void logout() throws IOException {
        logToFile("Logout successful: ", User.getUserName());
        userId = 0;
        userName = "";
    }

    /**
     * Getter for the user ID.
     * @return user ID
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Getter for the username.
     * @return username
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Saves login and logout history to a file.
     * @param message string indicating login/logout success or failure
     * @param userName the username
     * @throws IOException throw file I/O errors back to the JVM
     */
    public static void logToFile (String message, String userName) throws IOException {
        File file = new File("login_activity.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(message + "[User: " + userName + "]" + " [Date: " + LocalDate.now() + " " + LocalTime.now().toString().substring(0, 8) + "]\n");
        fileWriter.close();
    }
}
