package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Database queries related to appointments and users.
 */
@SuppressWarnings("DuplicatedCode")
public class AppointmentsQuery {

    /**
     * Queries the database for all the appointments.
     * @return list of appointments
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.contact_id=c.contact_id";
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Appointment appointment = new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Contact_Name"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime().toLocalDate(),
                    resultSet.getTimestamp("Start").toLocalDateTime().toLocalTime(),
                    resultSet.getTimestamp("End").toLocalDateTime().toLocalDate(),
                    resultSet.getTimestamp("End").toLocalDateTime().toLocalTime(),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID"));
            appointments.add(appointment);
        }
        return appointments;
    }

    /**
     * Queries the database for all the users.
     * @return list of strings in the format of username[user ID].
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<String> getUsers() throws SQLException {
        String sql = "SELECT User_Name, User_ID FROM users";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ObservableList<String> users = FXCollections.observableArrayList();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String user = resultSet.getString("User_Name") + " [" + resultSet.getInt("User_ID") + "]";
            users.add(user);
        }
        return users;
    }

    /**
     * Queries the database for the user with the ID passed.
     * @param userId the user ID of the user to get the name of
     * @return a string in the format of username[user ID].
     * @throws SQLException these errors are handled by the caller
     */
    public static String getUserById(int userId) throws SQLException {
        String sql = "SELECT User_Name, User_ID FROM users WHERE User_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("User_Name") + " [" + resultSet.getInt("User_ID") + "]";
    }

    /**
     * Queries the database to add the passed appointment.
     * @param appointment the appointment to add
     * @throws SQLException these errors are handled by the caller
     */
    public static void add(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime())));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime())));
        preparedStatement.setInt(7, appointment.getCustomerID());
        preparedStatement.setInt(8, appointment.getContactID());
        preparedStatement.setInt(9, appointment.getUserID());
        preparedStatement.executeUpdate();
    }

    /**
     * Queries the database to update the passed appointment.
     * @param appointment the appointment to update
     * @throws SQLException these errors are handled by the caller
     */
    public static void update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, appointment.getTitle());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getLocation());
        preparedStatement.setString(4, appointment.getType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(appointment.getStartDate(), appointment.getStartTime())));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.of(appointment.getEndDate(), appointment.getEndTime())));
        preparedStatement.setInt(7, appointment.getCustomerID());
        preparedStatement.setInt(8, appointment.getContactID());
        preparedStatement.setInt(9, appointment.getUserID());
        preparedStatement.setInt(10, appointment.getId());
        preparedStatement.executeUpdate();
    }

    /**
     * Queries the database to delete the passed appointment.
     * @param appointment the appointment to delete
     * @throws SQLException these errors are handled by the caller
     */
    public static void delete(Appointment appointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, appointment.getId());
        preparedStatement.executeUpdate();
    }

    /**
     * Checks if any duplicate appointments exist. An appointment at the same time but with a different user and customer is considered different.
     * @param start the start time and date
     * @param end the end time and date
     * @param customerId the customer ID to check if overlap should be allowed
     * @param userId the user ID to check if overlap should be allowed
     * @return the number of duplicates (add expects 0 and edit expects 1)
     * @throws SQLException these errors are handled by the caller
     */
    public static int checkForDuplicate(LocalDateTime start, LocalDateTime end, int customerId, int userId) throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) AS duplicates FROM appointments WHERE (((Start >= ? AND Start < ?) OR (End > ? AND End <= ?)) AND Customer_ID = ?) OR (((Start >= ? AND Start < ?) OR (End > ? AND End <= ?)) AND User_ID = ?)";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(end));
        preparedStatement.setInt(5, customerId);
        preparedStatement.setTimestamp(6, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(end));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(9, Timestamp.valueOf(end));
        preparedStatement.setInt(10, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("duplicates");
    }
}