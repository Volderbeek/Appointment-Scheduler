package controller;

import database.CustomersQuery;
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
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the report showing all appointments for the selected contact.
 */
public class ContactScheduleReportController implements Initializable {

    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private TableView<Appointment> scheduleTable;
    @FXML
    private TableColumn<Appointment, Integer> idColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> startTimeColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> endDateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> endTimeColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    private ObservableList<Appointment> appointments;

    /**
     * Initializes the contact selection combo box by calling populateComboBox.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBox();
    }

    /**
     * Navigates back to the main screen when the Back button is clicked.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Appointments.fxml")));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 1200, 600));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Calls the refresh method and the populateComboBox method when the Refresh button is clicked. These both involve database queries.
     */
    @FXML
    private void refreshButtonClicked() {
        refresh();
        populateComboBox();
    }

    /**
     * Calls the refresh method and the populateTable method when a contact is selected. This queries the database for the filtered data.
     */
    @FXML
    private void contactComboBoxSelected() {
        refresh();
        populateTable();
    }

    /**
     * Queries the database for the appointments of the selected contact.
     */
    private void refresh() {
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            try {
                appointments = ReportsQuery.getAppointmentsByContactId(selectedContact.getId());
            }
            catch (SQLException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached! Try clicking refresh and/or check the database.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Assigns the table the filtered data from the refresh method.
     */
    private void populateTable() {
        scheduleTable.setItems(appointments);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        scheduleTable.getSortOrder().add(idColumn);
    }

    /**
     * Queries the database for the list of contacts and assigns them to the combo box.
     */
    private void populateComboBox() {
        try {
            contactComboBox.setItems(CustomersQuery.getContacts());
        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached! Try clicking refresh and/or check the database.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
