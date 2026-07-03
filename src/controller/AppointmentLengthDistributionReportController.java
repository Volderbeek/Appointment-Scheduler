package controller;

import database.ReportsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.DistributionReport;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the pie chart view.
 */
public class AppointmentLengthDistributionReportController implements Initializable {

    @FXML
    private PieChart pieChart;

    /**
     * Initializes the view by calling the refresh method which loads the data.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    /**
     * Navigate back to the main screen.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Appointments.fxml")));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    /**
     * Calls the refresh method when the Refresh button is clicked which reloads the data.
     */
    @FXML
    private void refreshButtonClicked() {
        refresh();
    }

    /**
     * Loads the relevant data from the database and attaches it to the pie chart.
     * <p><p></p>
     * <b>LAMBDA #3:</b> Used to call an anonymous function on each element of the list returned from the database. This is a very appropriate usage of the forEach method in the functional paradigm which naturally uses anonymous functions.
     * </p>
     */
    private void refresh() {
        pieChart.getData().clear();
        try {
            ArrayList<DistributionReport> data = ReportsQuery.getAppointmentLengthDistribution();
            data.forEach(d -> pieChart.getData().add(new PieChart.Data(d.getLength() + " Minutes", d.getCount())));
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached. Click Refresh to try loading again.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
