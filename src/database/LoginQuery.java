package database;

import model.Appointment;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database queries related to logging in.
 */
public class LoginQuery {

    /**
     * Checks if the username and password passed to it are in the table of users in the database.
     * @param userName the username to check
     * @param password the password associated with the username passed
     * @return user ID or 0 if none found
     */
    public static int checkLogin(String userName, String password) {
        try {
            String sql = "SELECT User_ID, User_Name, Password FROM users";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("User_Name").equals(userName)) {
                    if (resultSet.getString("Password").equals(password)) {
                        return resultSet.getInt("User_ID");
                    }
                }
            }
            return 0;
        }
        catch (SQLException exception) {
            return 0;
        }
    }

    /**
     * Checks if any appointments are coming up or should already be occurring in the database.
     * @return the list of upcoming and late appointments
     */
    public static ArrayList<Appointment> checkAppointments() {
        try {
            String sql = "SELECT * FROM appointments WHERE (datetime('now', 'localtime') BETWEEN datetime(Start, '-15 minutes') AND datetime(End)) AND User_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            ArrayList<Appointment> appointments = new ArrayList<>();
            preparedStatement.setInt(1, User.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        JDBC.parseLocalDateTime(resultSet.getString("Start")),
                        JDBC.parseLocalDateTime(resultSet.getString("End"))
                );
                appointments.add(appointment);
            }
            return appointments;
        }
        catch (SQLException exception) {
            return null;
        }
    }
}
