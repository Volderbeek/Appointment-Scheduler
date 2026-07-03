package controller;

import database.CustomersQuery;
import helper.AppointmentSettings;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Customers view.
 */
public class CustomersController implements Initializable {

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    private TableColumn<Customer, String> divisionColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;

    /**
     * Initializes the table of customers by calling the refresh method.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    /**
     * Returns to the Appointments view by calling the goBack method.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void backButtonClicked(ActionEvent actionEvent) throws IOException {
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
     * Navigates to the screen to add customers.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void addButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root, 600, 500));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Saves selected customer information and navigates to the screen to update customer information.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void editButtonClicked(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            AppointmentSettings.setCustomer(selectedCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/EditCustomer.fxml")));
            stage.setTitle("Edit Customer");
            stage.setScene(new Scene(root, 600, 500));
            stage.centerOnScreen();
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing selected!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Deletes customer when Delete button is clicked.
     */
    @FXML
    private void deleteButtonClicked() {
        Customer customer = customersTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this customer? This will also delete all of this customer's appointments.");
            Optional<ButtonType> choice = confirm.showAndWait();
            if (choice.isPresent()) {
                if (choice.get().equals(ButtonType.OK)) {
                    try {
                        CustomersQuery.delete(customer);
                        Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
                        deletedAlert.setContentText("Customer: [ID: " + customer.getId() + "]" +
                                " [Name: " + customer.getName() + "] deleted.");
                        deletedAlert.showAndWait();
                        refresh();
                    } catch (SQLException exception) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Database could not be reached! Try again.", ButtonType.OK);
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
     * Attaches the list of customers to the TableView.
     * @param customers the list of customers retrieved from the database
     */
    private void populateTable(ObservableList<Customer> customers) {
        customersTable.setItems(customers);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        customersTable.getSortOrder().add(idColumn);
    }

    /**
     * Loads customers from the database and calls populateTable with the retrieved data.
     */
    private void refresh() {
        try {
            ObservableList<Customer> customers = CustomersQuery.getCustomers();
            populateTable(customers);
        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Database could not be reached! Try clicking refresh and/or check the database.",
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Returns to the Customers view. Used when clicking the Back button.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    private void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/view/Appointments.fxml")));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 1200, 600));
        stage.centerOnScreen();
        stage.show();
    }
}
