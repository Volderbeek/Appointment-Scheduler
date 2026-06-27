package controller;

import database.ReportsQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AppointmentTypeReport;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the report screen showing appointment counts grouped by month
 * and type.
 */
public class AppointmentTypeReportController implements Initializable {

    @FXML
    private TableView<AppointmentTypeReport> typeReportTable;
    @FXML
    private TableColumn<AppointmentTypeReport, String> typeReportColumn;
    @FXML
    private TableColumn<AppointmentTypeReport, Integer> countColumn;
    // @SuppressWarnings("unused")
    @FXML
    private ToggleGroup monthToggleGroup;

    /**
     * Initialize is not used here as there is no default selection.
     * 
     * @param url            see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Navigate back to the main screen.
     * 
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Appointments.fxml")));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    /**
     * Calls populateTable with the month of January.
     */
    @FXML
    private void janToggleSelected() {
        populateTable(Month.JANUARY);
    }

    /**
     * Calls populateTable with the month of February.
     */
    @FXML
    private void febToggleSelected() {
        populateTable(Month.FEBRUARY);
    }

    /**
     * Calls populateTable with the month of March.
     */
    @FXML
    private void marToggleSelected() {
        populateTable(Month.MARCH);
    }

    /**
     * Calls populateTable with the month of April.
     */
    @FXML
    private void aprToggleSelected() {
        populateTable(Month.APRIL);
    }

    /**
     * Calls populateTable with the month of May.
     */
    @FXML
    private void mayToggleSelected() {
        populateTable(Month.MAY);
    }

    /**
     * Calls populateTable with the month of June.
     */
    @FXML
    private void junToggleSelected() {
        populateTable(Month.JUNE);
    }

    /**
     * Calls populateTable with the month of July.
     */
    @FXML
    private void julToggleSelected() {
        populateTable(Month.JULY);
    }

    /**
     * Calls populateTable with the month of August.
     */
    @FXML
    private void augToggleSelected() {
        populateTable(Month.AUGUST);
    }

    /**
     * Calls populateTable with the month of September.
     */
    @FXML
    private void sepToggleSelected() {
        populateTable(Month.SEPTEMBER);
    }

    /**
     * Calls populateTable with the month of October.
     */
    @FXML
    private void octToggleSelected() {
        populateTable(Month.OCTOBER);
    }

    /**
     * Calls populateTable with the month of November.
     */
    @FXML
    private void novToggleSelected() {
        populateTable(Month.NOVEMBER);
    }

    /**
     * Calls populateTable with the month of December.
     */
    @FXML
    private void decToggleSelected() {
        populateTable(Month.DECEMBER);
    }

    /**
     * Calls the database with the month, and it returns the subsequent count by
     * type and month.
     * 
     * @param month the month used in the filter
     */
    private void populateTable(Month month) {
        try {
            ObservableList<AppointmentTypeReport> appointmentTypeReports = ReportsQuery.getAppointmentsByType(month);
            typeReportTable.setItems(appointmentTypeReports);

            typeReportColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        } catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
