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
 * Controller for the Edit Appointment view
 */
@SuppressWarnings({"FieldCanBeLocal", "DuplicatedCode"})
public class EditAppointmentController implements Initializable {

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
    private TextField typeBox;
    @FXML
    private ComboBox<String> userComboBox;

    private ObservableList<String> hour;
    private ObservableList<String> minute;
    private ObservableList<Customer> customers;
    private ObservableList<Contact> contacts;
    private ObservableList<String> users;
    private final String css = AppointmentSettings.getCssStyles();
    private Appointment appointment;
    private Customer selectedCustomer;
    private Contact selectedContact;
    private String selectedUser;

    /**
     * Sets up the combo boxes that don't need database access and then calls the refresh method which makes the database calls and catches associated errors.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointment = AppointmentSettings.getAppointment();
        hour = FXCollections.observableArrayList();
        minute = FXCollections.observableArrayList();
        hour.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minute.addAll("00", "15", "30", "45");
        startHourComboBox.setItems(hour);
        endHourComboBox.setItems(hour);
        startMinuteComboBox.setItems(minute);
        endMinuteComboBox.setItems(minute);
        refresh();
        reset();
    }

    /**
     * A convenience method that sets the end date picker when a start date is selected.
     */
    @FXML
    private void startDatePicked() {
        endDatePicker.setValue(startDatePicker.getValue());
    }

    /**
     * Calls validation helper functions and updates appointment in the database if successful.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void saveButtonClicked(ActionEvent actionEvent) throws IOException {
        int customerId = 0;
        if (ValidateAppointments.checkTitle(titleBox.getText())) titleBox.setStyle(css);
        else titleBox.setStyle(null);
        if (ValidateAppointments.checkDescription(descriptionBox.getText())) descriptionBox.setStyle(css);
        else descriptionBox.setStyle(null);
        if (ValidateAppointments.checkLocation(locationBox.getText())) locationBox.setStyle(css);
        else locationBox.setStyle(null);
        if (ValidateAppointments.checkType(typeBox.getText())) typeBox.setStyle(css);
        else typeBox.setStyle(null);
        if (ValidateAppointments.checkCustomer(customerComboBox.getSelectionModel().getSelectedItem())) customerComboBox.setStyle(css);
        else {
            customerComboBox.setStyle(null);
            customerId = customerComboBox.getSelectionModel().getSelectedItem().getId();
        }
        if (ValidateAppointments.checkContact(contactComboBox.getSelectionModel().getSelectedItem())) contactComboBox.setStyle(css);
        else contactComboBox.setStyle(null);
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime startTime = LocalTime.of(Integer.parseInt(startHourComboBox.getSelectionModel().getSelectedItem()),
                Integer.parseInt(startMinuteComboBox.getSelectionModel().getSelectedItem()));
        LocalTime endTime = LocalTime.of(Integer.parseInt(endHourComboBox.getSelectionModel().getSelectedItem()),
                Integer.parseInt(endMinuteComboBox.getSelectionModel().getSelectedItem()));
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        int userId;
        if (userComboBox.getSelectionModel().getSelectedItem().isEmpty()) {
            userId = appointment.getUserID();
        }
        else {
            String userNameAndId = userComboBox.getSelectionModel().getSelectedItem();
            userId = Integer.parseInt(userNameAndId.substring(userNameAndId.indexOf("[") + 1, userNameAndId.length() - 1));
        }
        Appointment updatedAppointment = new Appointment(
                appointment.getId(),
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
        if (updatedAppointment.equals(appointment)) {
            ValidateAppointments.setNumberOfDuplicates(1);
        }
        else {
            ValidateAppointments.setNumberOfDuplicates(0);
        }
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
            try {
                AppointmentsQuery.update(updatedAppointment);
                goBack(actionEvent);
            }
            catch (SQLException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Yellow fields were entered incorrectly. Please fix and try again.\n\n" + ValidateAppointments.getErrorMessages());
            alert.showAndWait();
            ValidateAppointments.clearErrors();

        }
    }

    /**
     * Resets all fields back to the appointment currently being edited when the Reset button is clicked.
     */
    @FXML
    private void resetButtonClicked() {
        reset();
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
     * Returns to the Appointments view. Used when successfully updating an appointment or clicking the Back button.
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
     * Gets data for the form from the database. Also gets the selection information from the previous screen saved to a helper class. Called initially and by clicking the Refresh button.
     */
    private void refresh() {
        try {
            customers = CustomersQuery.getCustomers();
            contacts = CustomersQuery.getContacts();
            users = AppointmentsQuery.getUsers();
            customerComboBox.setItems(customers);
            contactComboBox.setItems(contacts);
            userComboBox.setItems(users);
            selectedCustomer = CustomersQuery.getCustomerById(appointment.getCustomerID());
            selectedContact = CustomersQuery.getContactById(appointment.getContactID());
            selectedUser = AppointmentsQuery.getUserById(appointment.getUserID());
            userComboBox.getSelectionModel().select(selectedUser);
        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load info from database! Click \"Back\" or \"Refresh\".", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Resets all fields back to the appointment currently being edited.
     */
    private void reset() {
        titleBox.setText(appointment.getTitle());
        titleBox.setStyle(null);
        descriptionBox.setText(appointment.getDescription());
        descriptionBox.setStyle(null);
        locationBox.setText(appointment.getLocation());
        locationBox.setStyle(null);
        typeBox.setText(appointment.getType());
        typeBox.setStyle(null);
        customerComboBox.getSelectionModel().select(selectedCustomer);
        customerComboBox.setStyle(null);
        contactComboBox.getSelectionModel().select(selectedContact);
        contactComboBox.setStyle(null);
        startDatePicker.setValue(appointment.getStartDate());
        startHourComboBox.getSelectionModel().select(appointment.getStartTime().toString().substring(0, 2));
        startHourComboBox.setStyle(null);
        startMinuteComboBox.getSelectionModel().select(appointment.getStartTime().toString().substring(3, 5));
        startMinuteComboBox.setStyle(null);
        endDatePicker.setValue(appointment.getEndDate());
        endHourComboBox.getSelectionModel().select(appointment.getEndTime().toString().substring(0, 2));
        endHourComboBox.setStyle(null);
        endMinuteComboBox.getSelectionModel().select(appointment.getEndTime().toString().substring(3, 5));
        endMinuteComboBox.setStyle(null);
        userComboBox.getSelectionModel().select(selectedUser);
    }
}
