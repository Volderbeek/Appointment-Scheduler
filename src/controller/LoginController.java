package controller;

import database.LoginQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the Login view.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameBox;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private Label timeZoneLabel;
    private ResourceBundle resourceBundle;

    /**
     * Initializes the login screen by getting the resourceBundle for the language settings and setting the timezone label to the local timezone.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        timeZoneLabel.setText(resourceBundle.getString("time.zone") + ZoneId.systemDefault());
    }

    /**
     * Handles all the login activities after Login button is clicked.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void loginButtonClicked(ActionEvent actionEvent) throws IOException {
        String userName = usernameBox.getText();
        String password = passwordBox.getText();
        int userId = LoginQuery.checkLogin(userName, password);
        StringBuilder messages = new StringBuilder();
        if (userId > 0) {
            User.login(userId, userName);
            ArrayList<Appointment> appointments = LoginQuery.checkAppointments();
            if (Objects.requireNonNull(appointments).isEmpty()) {
                Alert noAppointments = new Alert(Alert.AlertType.INFORMATION);
                noAppointments.setContentText(resourceBundle.getString("no.upcoming.message"));
                noAppointments.showAndWait();
            }
            else {
                for (Appointment appointment : appointments) {
                    if (LocalTime.now().isAfter(appointment.getStartTime()) && LocalTime.now().isBefore(appointment.getEndTime().plusMinutes(1))) {
                        messages.append(resourceBundle.getString("late.message"));
                        messages.append("\nID: ").append(appointment.getId());
                    }
                    else {
                        messages.append(resourceBundle.getString("upcoming.message"));
                        messages.append("\nID: ").append(appointment.getId());
                    }
                    messages.append("   Date: ").append(appointment.getStartDate());
                    messages.append("   Time: ").append(appointment.getStartTime()).append(" - ").append(appointment.getEndTime());
                    messages.append("\n\n");
                }
                Alert appointmentMessage = new Alert(Alert.AlertType.INFORMATION);
                appointmentMessage.setContentText(messages.toString());
                appointmentMessage.showAndWait();
            }

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Appointments.fxml")));
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();
            User.logToFile("Login successful: ", userName);
        }
        else {
            User.logToFile("Login failed: ", userName);
            Alert alert = new Alert(Alert.AlertType.ERROR, resourceBundle.getString("error.text"), ButtonType.OK);
            alert.showAndWait();
        }
    }
}