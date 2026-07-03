package controller;

import database.AppointmentsQuery;
import helper.AppointmentSettings;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the Appointments view, the main screen of the application.
 */
public class AppointmentsController implements Initializable {

    private ObservableList<Appointment> appointments;
    private ObservableList<Appointment> appointmentsByMonth;
    private ObservableList<Appointment> appointmentsByWeek;

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private ToggleGroup appointmentViewToggle;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label timezoneLabel;
    @FXML
    private TableColumn<Appointment, Integer> idColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
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
    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    private Label userNameLabel;
    @FXML
    private RadioButton monthToggle;
    @FXML
    private RadioButton allToggle;
    @FXML
    private RadioButton weekToggle;

    /**
     * Initializes a few UI elements, saves user information, and calls the refresh method.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentSettings.setUserNameAndId("User: " + User.getUserName() + " [" + User.getUserId() + "]");
        userNameLabel.setText(AppointmentSettings.getUserNameAndId());
        timezoneLabel.setText("Your Timezone: " + ZoneId.systemDefault());
        datePicker.setValue(LocalDate.now());
        refresh();
    }

    /**
     * Navigates to the Customers view.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void customersButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Customers.fxml")));
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 800, 550));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Navigates back to the login screen and logs the user out.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void logoutButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        User.logout();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language/Lang", Locale.getDefault());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")), resourceBundle);
        Platform.runLater(root::requestFocus);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 320, 400));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Navigates to the Add Appointment screen.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void addButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 600, 649));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Saves selected appointment information and navigates to the Edit Appointment screen.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void editButtonClicked(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            AppointmentSettings.setAppointment(selectedAppointment);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/EditAppointment.fxml")));
            stage.setTitle("Edit Appointment");
            stage.setScene(new Scene(root, 600, 649));
            stage.centerOnScreen();
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing selected!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Deletes appointment when Delete button is clicked.
     */
    @FXML
    private void deleteButtonClicked() {
        Appointment appointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (appointment != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment? This is permanent.");
            Optional<ButtonType> choice = confirm.showAndWait();
            if (choice.isPresent()) {
                if (choice.get().equals(ButtonType.OK)) {
                    try {
                        AppointmentsQuery.delete(appointment);
                        Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
                        deletedAlert.setContentText("Appointment: [ID: " + appointment.getId() + "]" + " [Type: " + appointment.getType() + "] deleted.");
                        deletedAlert.showAndWait();
                        refresh();
                    } catch (SQLException exception) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached! Try again.", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing selected!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Calls the refresh method when the Refresh button is clicked.
     */
    @FXML
    private void refreshButtonClicked() {
        refresh();
    }

    /**
     * Calls the populateTable method with a list of appointments filtered by month.
     */
    @FXML
    private void monthToggleSelected() {
        populateTable(appointmentsByMonth);
    }

    /**
     * Calls the populateTable method with a list of appointments filtered by week.
     */
    @FXML
    private void weekToggleSelected() {
        populateTable(appointmentsByWeek);
    }

    /**
     * Calls the populateTable method with a list of all appointments.
     */
    @FXML
    private void allToggleSelected() {
        populateTable(appointments);
    }

    /**
     * Calls the refresh method when a new date is picked for filters.
     */
    @FXML
    private void datePicked() {
        refresh();
    }

    /**
     * Navigates to the reports screen showing appointment counts grouped by month and type.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void appointmentTypeButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentTypeReport.fxml")));
        stage.setTitle("Appointment Type Report");
        stage.setScene(new Scene(root, 600, 400));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Navigates to the reports screen showing appointments for the selected contact.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void contactScheduleButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ContactScheduleReport.fxml")));
        stage.setTitle("Contact Schedule Report");
        stage.setScene(new Scene(root, 800, 600));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Navigates to the reports screen showing a pie chart of appointments by length of time.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void appointmentLengthDistributionButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentLengthDistributionReport.fxml")));
        stage.setTitle("Appointment Length Distribution Report");
        stage.setScene(new Scene(root, 650, 550));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Attaches a list of filtered appointments to the TableView.
     * @param appointments the list of filtered appointments
     */
    private void populateTable(ObservableList<Appointment> appointments) {

        appointmentsTable.setItems(appointments);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        appointmentsTable.getSortOrder().add(idColumn);
    }

    /**
     * Loads appointments from the database and filters them by month and week allowing the TableView to be filtered. Calls populateTable with the filtered data.
     * <p><p></p>
     * <b>LAMBDA #1:</b> Used to cleanly filter appointments by picked month. This is a very appropriate usage of the filter method in the functional paradigm which naturally uses anonymous functions.
     * </p>
     * <p><p></p>
     * <b>LAMBDA #2:</b> Used to cleanly filter appointments by picked week. This is a very appropriate usage of the filter method in the functional paradigm which naturally uses anonymous functions.
     * </p>
     */
    private void refresh() {
        try {
            appointments = AppointmentsQuery.getAppointments();
            LocalDate pickedDate = datePicker.getValue();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            // Lambda #1
            appointmentsByMonth = appointments.stream().filter(a ->
                    (a.getStartDate().getYear() == pickedDate.getYear() || a.getEndDate().getYear() == pickedDate.getYear()) &&
                            (a.getStartDate().getMonthValue() == pickedDate.getMonthValue() || a.getEndDate().getMonthValue() == pickedDate.getMonthValue())
            ).collect(Collectors.toCollection(FXCollections::observableArrayList));
            // Lambda #2
            appointmentsByWeek = appointments.stream().filter(a ->
                    a.getStartDate().get(weekFields.weekOfWeekBasedYear()) == pickedDate.get(weekFields.weekOfWeekBasedYear()) &&
                            a.getEndDate().get(weekFields.weekOfWeekBasedYear()) == pickedDate.get(weekFields.weekOfWeekBasedYear())
            ).collect(Collectors.toCollection(FXCollections::observableArrayList));
            if (appointmentViewToggle.getSelectedToggle().equals(allToggle)) {
                populateTable(appointments);
            }
            else if (appointmentViewToggle.getSelectedToggle().equals(monthToggle)) {
                populateTable(appointmentsByMonth);
            }
            else if (appointmentViewToggle.getSelectedToggle().equals(weekToggle)) {
                populateTable(appointmentsByWeek);
            }

        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached! Try clicking refresh and/or check the database.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
