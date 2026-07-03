package controller;

import database.CustomersQuery;
import helper.AppointmentSettings;
import helper.ValidateCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the Edit Customer view.
 */
@SuppressWarnings("DuplicatedCode")
public class EditCustomerController implements Initializable {

    @FXML
    private TextField nameBox;
    @FXML
    private TextField addressBox;
    @FXML
    private TextField postalCodeBox;
    @FXML
    private ComboBox<Division> divisionComboBox;
    @FXML
    private TextField phoneNumberBox;
    @FXML
    private TextField countryBox;

    private final String css = AppointmentSettings.getCssStyles();
    private Customer customer;
    private Division selectedDivision;

    /**
     * Calls the refresh and reset methods when the view is initialized. Also gets the selected customer from the previous screen saved to a helper class.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = AppointmentSettings.getCustomer();
        refresh();
        reset();
    }

    /**
     * Calls validation helper functions and updates customer in the database if successful.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void updateButtonClicked(ActionEvent actionEvent) throws IOException {
        if (ValidateCustomers.checkName(nameBox.getText())) nameBox.setStyle(css);
        else nameBox.setStyle(null);
        if (ValidateCustomers.checkAddress(addressBox.getText())) addressBox.setStyle(css);
        else addressBox.setStyle(null);
        if (ValidateCustomers.checkPostalCode(postalCodeBox.getText())) postalCodeBox.setStyle(css);
        else postalCodeBox.setStyle(null);
        if (ValidateCustomers.checkPhoneNumber(phoneNumberBox.getText())) phoneNumberBox.setStyle(css);
        else phoneNumberBox.setStyle(null);
        Division division = divisionComboBox.getSelectionModel().getSelectedItem();
        String divisionName;
        if (division != null) divisionName = division.getName();
        else divisionName = "";
        if (ValidateCustomers.checkDivision(divisionName)) divisionComboBox.setStyle(css);
        else divisionComboBox.setStyle(null);

        if (ValidateCustomers.isValid()) {
            Customer updatedCustomer = new Customer(
                    customer.getId(),
                    nameBox.getText(),
                    addressBox.getText(),
                    postalCodeBox.getText(),
                    phoneNumberBox.getText(),
                    divisionComboBox.getSelectionModel().getSelectedItem().getId(),
                    divisionComboBox.getSelectionModel().getSelectedItem().getName(),
                    divisionComboBox.getSelectionModel().getSelectedItem().getCountryName()
            );
            try {
                CustomersQuery.update(updatedCustomer);
                goBack(actionEvent);
            } catch (SQLException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Database could not be reached.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Yellow fields were entered incorrectly. Please fix and try again.\n\n" + ValidateCustomers.getErrorMessages());
            alert.showAndWait();
            ValidateCustomers.clearErrors();
        }
    }

    /**
     * Resets all fields back to the customer currently being edited when the Reset button is clicked.
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
     * Sets country text field when division is selected as country is always matched with the corresponding division.
     */
    @FXML
    private void divisionSelected() {
        Division currentDivision = divisionComboBox.getSelectionModel().getSelectedItem();
        if (currentDivision != null) {
            countryBox.setText(currentDivision.getCountryName());
        }
    }

    /**
     * Returns to the Customers view. Used when successfully updating a customer or clicking the Back button.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    private void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Customers.fxml")));
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 800, 550));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Gets data for the form from the database. Also gets the selected division from the previous screen saved to a helper class. Called initially and by clicking the Refresh button.
     */
    private void refresh() {
        try {
            ObservableList<Division> divisions = CustomersQuery.getDivisions();
            divisionComboBox.setItems(divisions);
            selectedDivision = CustomersQuery.getDivisionById(customer.getDivisionId());
        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load info from database! Click \"Back\" or \"Refresh\".", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Resets all fields back to the customer currently being edited.
     */
    private void reset() {
        nameBox.setText(customer.getName());
        nameBox.setStyle(null);
        addressBox.setText(customer.getAddress());
        addressBox.setStyle(null);
        postalCodeBox.setText(customer.getPostalCode());
        postalCodeBox.setStyle(null);
        phoneNumberBox.setText(customer.getPhoneNumber());
        phoneNumberBox.setStyle(null);
        divisionComboBox.getSelectionModel().select(selectedDivision);
        divisionComboBox.setStyle(null);
        countryBox.setText(customer.getCountry());
    }
}
