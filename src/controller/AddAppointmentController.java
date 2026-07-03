package controller;

import database.AppointmentsQuery;
import database.CustomersQuery;
import helper.AppointmentSettings;
import helper.ValidateAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the Add Appointment view.
 */
@SuppressWarnings({"DuplicatedCode", "FieldCanBeLocal"})
public class AddAppointmentController implements Initializable {

    @FXML
    private TextField titleBox;
    @FXML
    private TextField descriptionBox;
    @FXML
    private TextField locationBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private ComboBox<String> startHourComboBox;
    @FXML
    private ComboBox<String> startMinuteComboBox;
    @FXML
    private ComboBox<String> endHourComboBox;
    @FXML
    private ComboBox<String> endMinuteComboBox;
    @FXML
    private ComboBox<String> userComboBox;
    @FXML
    private TextField typeBox;

    private ObservableList<String> hour;
    private ObservableList<String> minute;
    private ObservableList<Customer> customers;
    private ObservableList<Contact> contacts;
    private ObservableList<String> users;
    private final String css = AppointmentSettings.getCssStyles();

    /**
     * Sets up the combo boxes that don't need database access and then calls the refresh method which makes the database calls and catches associated errors.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        hour = FXCollections.observableArrayList();
        minute = FXCollections.observableArrayList();
        hour.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minute.addAll("00", "15", "30", "45");
        startHourComboBox.setItems(hour);
        startHourComboBox.getSelectionModel().selectFirst();
        endHourComboBox.setItems(hour);
        endHourComboBox.getSelectionModel().selectFirst();
        startMinuteComboBox.setItems(minute);
        startMinuteComboBox.getSelectionModel().selectFirst();
        endMinuteComboBox.setItems(minute);
        endMinuteComboBox.getSelectionModel().selectFirst();
        refresh();
    }

    /**
     * A convenience method that sets the end date picker when a start date is selected.
     */
    @FXML
    private void startDatePicked() {
        endDatePicker.setValue(startDatePicker.getValue());
    }

    /**
     * Calls validation helper functions and adds appointment to the database if successful.
     * <p>
     * NOTES: Validation methods (except time and date) simply check if fields are empty now but could be expanded with this modular approach.
     * </p>
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void addButtonClicked(ActionEvent actionEvent) throws IOException {
        int customerId = 0;
        if (ValidateAppointments.checkTitle(titleBox.getText())) titleBox.setStyle(css);
        else titleBox.setStyle(null);
        if (ValidateAppointments.checkDescription(descriptionBox.getText())) descriptionBox.setStyle(css);
        else descriptionBox.setStyle(null);
        if (ValidateAppointments.checkLocation(locationBox.getText())) locationBox.setStyle(css);
        else locationBox.setStyle(null);
        if (ValidateAppointments.checkType(typeBox.getText())) typeBox.setStyle(css);
        else typeBox.setStyle(null);
        if (ValidateAppointments.checkCustomer(customerComboBox.getSelectionModel().getSelectedItem()))
            customerComboBox.setStyle(css);
        else {
            customerComboBox.setStyle(null);
            customerId = customerComboBox.getSelectionModel().getSelectedItem().getId();
        }
        if (ValidateAppointments.checkContact(contactComboBox.getSelectionModel().getSelectedItem())) contactComboBox.setStyle(css);
        else contactComboBox.setStyle(null);
        if (userComboBox.getSelectionModel().getSelectedItem().isEmpty())
            userComboBox.getSelectionModel().select(AppointmentSettings.getUserNameAndId());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime startTime = LocalTime.of(Integer.parseInt(startHourComboBox.getSelectionModel().getSelectedItem()),
                Integer.parseInt(startMinuteComboBox.getSelectionModel().getSelectedItem()));
        LocalTime endTime = LocalTime.of(Integer.parseInt(endHourComboBox.getSelectionModel().getSelectedItem()),
                Integer.parseInt(endMinuteComboBox.getSelectionModel().getSelectedItem()));
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        ValidateAppointments.setNumberOfDuplicates(0);
        String userNameAndId = userComboBox.getSelectionModel().getSelectedItem();
        int userId = Integer.parseInt(userNameAndId.substring(userNameAndId.indexOf("[") + 1, userNameAndId.length() - 1));
        if (ValidateAppointments.checkTimes(start, end, customerId, userId)) {
            startHourComboBox.setStyle(css);
            startMinuteComboBox.setStyle(css);
            endHourComboBox.setStyle(css);
            endMinuteComboBox.setStyle(css);
        }
        else {
            startHourComboBox.setStyle(null);
            startMinuteComboBox.setStyle(null);
            endHourComboBox.setStyle(null);
            endMinuteComboBox.setStyle(null);
        }
        if (ValidateAppointments.isValid()) {
            Appointment appointment = new Appointment(
                    titleBox.getText(),
                    descriptionBox.getText(),
                    locationBox.getText(),
                    contactComboBox.getSelectionModel().getSelectedItem().toString(),
                    typeBox.getText(),
                    start,
                    end,
                    customerComboBox.getSelectionModel().getSelectedItem().getId(),
                    userId,
                    contactComboBox.getSelectionModel().getSelectedItem().getId()
            );
            try {
                AppointmentsQuery.add(appointment);
                goBack(actionEvent);
            }
            catch (SQLException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Yellow fields were entered incorrectly. Please fix and try again.\n\n"
                    + ValidateAppointments.getErrorMessages());
            alert.showAndWait();
            ValidateAppointments.clearErrors();

        }
    }

    /**
     * Clears out all fields when the Clear button is clicked.
     */
    @FXML
    private void clearButtonClicked() {
        titleBox.setText("");
        titleBox.setStyle(null);
        descriptionBox.setText("");
        descriptionBox.setStyle(null);
        locationBox.setText("");
        locationBox.setStyle(null);
        typeBox.setText("");
        typeBox.setStyle(null);
        customerComboBox.getSelectionModel().clearSelection();
        customerComboBox.setStyle(null);
        contactComboBox.getSelectionModel().clearSelection();
        contactComboBox.setStyle(null);
        startDatePicker.setValue(LocalDate.now());
        startHourComboBox.getSelectionModel().selectFirst();
        startHourComboBox.setStyle(null);
        startMinuteComboBox.getSelectionModel().selectFirst();
        startMinuteComboBox.setStyle(null);
        endDatePicker.setValue(LocalDate.now());
        endHourComboBox.getSelectionModel().selectFirst();
        endHourComboBox.setStyle(null);
        endMinuteComboBox.getSelectionModel().selectFirst();
        endMinuteComboBox.setStyle(null);
        userComboBox.getSelectionModel().select(AppointmentSettings.getUserNameAndId());
    }

    /**
     * Calls the goBack method when the Back button is clicked.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void backButtonClicked(ActionEvent actionEvent) throws IOException{
        goBack(actionEvent);
    }

    /**
     * Calls the refresh method when the Refresh button is clicked.
     */
    @FXML
    private void refreshButtonClicked() {
        refresh();
    }

    /**
     * Returns to the Appointments view. Used when successfully adding an appointment or clicking the Back button.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    private void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Appointments.fxml")));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    /**
     * Gets data for the form from the database. Called initially and by clicking the Refresh button.
     */
    private void refresh() {
        try {
            customers = CustomersQuery.getCustomers();
            contacts = CustomersQuery.getContacts();
            users = AppointmentsQuery.getUsers();
            customerComboBox.setItems(customers);
            contactComboBox.setItems(contacts);
            userComboBox.setItems(users);
            userComboBox.getSelectionModel().select(AppointmentSettings.getUserNameAndId());
        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Could not load info from database! Click \"Back\" or \"Refresh\".",
                    ButtonType.OK);
            alert.showAndWait();
        }
    }
}
