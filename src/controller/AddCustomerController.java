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
 * Controller for the Add Customer view.
 */
@SuppressWarnings("DuplicatedCode")
public class AddCustomerController implements Initializable {

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

    /**
     * Calls the refresh method when the view is initialized.
     * @param url see JavaFX documentation
     * @param resourceBundle see JavaFX documentation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    /**
     * Calls validation helper functions and adds customer to the database if successful.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    @FXML
    private void addButtonClicked(ActionEvent actionEvent) throws IOException {
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
            Customer customer = new Customer(
                    nameBox.getText(),
                    addressBox.getText(),
                    postalCodeBox.getText(),
                    phoneNumberBox.getText(),
                    divisionComboBox.getSelectionModel().getSelectedItem().getId(),
                    divisionComboBox.getSelectionModel().getSelectedItem().getName(),
                    divisionComboBox.getSelectionModel().getSelectedItem().getCountryName()
            );
            try {
                CustomersQuery.add(customer);
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
     * Clears out all fields when the Clear button is clicked.
     */
    @FXML
    private void clearButtonClicked() {
        nameBox.setText("");
        nameBox.setStyle(null);
        addressBox.setText("");
        addressBox.setStyle(null);
        postalCodeBox.setText("");
        postalCodeBox.setStyle(null);
        divisionComboBox.getSelectionModel().clearSelection();
        countryBox.setText("");
        countryBox.setStyle(null);
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
     * Returns to the Customers view. Used when successfully adding a customer or clicking the Back button.
     * @param actionEvent see JavaFX documentation
     * @throws IOException throw file I/O errors back to the JVM
     */
    private void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Customers.fxml")));
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 800, 550));
        stage.show();
    }

    /**
     * Gets data for the form from the database. Called initially and by clicking the Refresh button.
     */
    private void refresh() {
        try {
            ObservableList<Division> divisions = CustomersQuery.getDivisions();
            divisionComboBox.setItems(divisions);
        }
        catch (SQLException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load info from database! Click \"Back\" or \"Refresh\".", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
