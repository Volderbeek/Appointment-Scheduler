package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.AppointmentTypeReport;
import model.DistributionReport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;

/**
 * Database queries related to the reports.
 */
public class ReportsQuery {

    /**
     * Queries the database for appointment counts grouped by month and type.
     * @param month the month to group by
     * @return the list of appointment reports
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<AppointmentTypeReport> getAppointmentsByType(Month month) throws SQLException {
        String sql = "SELECT Type, COUNT(Appointment_ID) AS count FROM appointments WHERE MONTH(Start) = ? OR MONTH(End) = ? GROUP BY Type";
        ObservableList<AppointmentTypeReport> appointmentTypeReports = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, month.getValue());
        preparedStatement.setInt(2, month.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            AppointmentTypeReport appointmentTypeReport = new AppointmentTypeReport(
                    resultSet.getString("Type"),
                    resultSet.getInt("count")
            );
            appointmentTypeReports.add(appointmentTypeReport);
        }
        return appointmentTypeReports;
    }

    /**
     * Queries the database for appointments associated with the passed contact.
     * @param contactId the ID of the contact
     * @return the list of appointments for the passed contact ID
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<Appointment> getAppointmentsByContactId(int contactId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, contactId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Appointment appointment = new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID"));
            appointments.add(appointment);
        }
        return appointments;
    }

    /**
     * Queries the database for the count of appointments grouped by length.
     * @return the list of distribution reports
     * @throws SQLException these errors are handled by the caller
     */
    public static ArrayList<DistributionReport> getAppointmentLengthDistribution() throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) AS count, TIMESTAMPDIFF(MINUTE, Start, End) AS length FROM appointments GROUP BY length";
        ArrayList<DistributionReport> distributionReports = new ArrayList<>();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            DistributionReport distributionReport = new DistributionReport(
                    resultSet.getInt("count"),
                    resultSet.getInt("length")
            );
            distributionReports.add(distributionReport);
        }
        return distributionReports;
    }
}
