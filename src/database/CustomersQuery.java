package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database queries related to customers, contacts, and divisions.
 */
public class CustomersQuery {

    /**
     * Queries the database for all the customers.
     * @return list of customers
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.Country_ID;";
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getInt("Customer_ID"),
                    resultSet.getString("Customer_Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Postal_Code"),
                    resultSet.getString("Phone"),
                    resultSet.getInt("Division_ID"),
                    resultSet.getString("Division"),
                    resultSet.getString("Country")
            );
            customers.add(customer);
        }
        return customers;
    }

    /**
     * Queries the database for the customer with the ID passed.
     * @param customerId the ID to find the customer with
     * @return the customer with the passed ID
     * @throws SQLException these errors are handled by the caller
     */
    public static Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.Country_ID WHERE Customer_ID = ?;";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Customer(
                resultSet.getInt("Customer_ID"),
                resultSet.getString("Customer_Name"),
                resultSet.getString("Address"),
                resultSet.getString("Postal_Code"),
                resultSet.getString("Phone"),
                resultSet.getInt("Division_ID"),
                resultSet.getString("Division"),
                resultSet.getString("Country")
        );
    }

    /**
     * Queries the database for all the contacts.
     * @return list of contacts
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<Contact> getContacts() throws SQLException {
        String sql = "SELECT * FROM contacts;";
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Contact contact = new Contact(
                    resultSet.getInt("Contact_ID"),
                    resultSet.getString("Contact_Name"),
                    resultSet.getString("Email")
            );
            contacts.add(contact);
        }
        return contacts;
    }

    /**
     * Queries the database for the contact with the ID passed.
     * @param contactId the ID to find the contact with
     * @return the contact with the passed ID
     * @throws SQLException these errors are handled by the caller
     */
    public static Contact getContactById(int contactId) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?;";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, contactId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Contact(
                resultSet.getInt("Contact_ID"),
                resultSet.getString("Contact_Name"),
                resultSet.getString("Email")
        );
    }

    /**
     * Queries the database for all the divisions.
     * @return list of divisions
     * @throws SQLException these errors are handled by the caller
     */
    public static ObservableList<Division> getDivisions() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions AS d INNER JOIN countries AS c ON d.Country_ID = c.Country_ID";
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Division division = new Division(
                    resultSet.getInt("Division_ID"),
                    resultSet.getString("Division"),
                    resultSet.getInt("Country_ID"),
                    resultSet.getString("Country")
            );
            divisions.add(division);
        }
        return divisions;
    }

    /**
     * Queries the database for the division with the ID passed.
     * @param divisionId the ID to find the division with
     * @return the division with the passed ID
     * @throws SQLException these errors are handled by the caller
     */
    public static Division getDivisionById(int divisionId) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions AS d INNER JOIN countries AS c ON d.Country_ID = c.Country_ID WHERE Division_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, divisionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Division(
                resultSet.getInt("Division_ID"),
                resultSet.getString("Division"),
                resultSet.getInt("Country_ID"),
                resultSet.getString("Country")
        );
    }

    /**
     * Queries the database to add the passed customer.
     * @param customer the customer to add
     * @throws SQLException these errors are handled by the caller
     */
    public static void add(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setInt(5, customer.getDivisionId());
        preparedStatement.executeUpdate();
    }

    /**
     * Queries the database to update the passed customer.
     * @param customer the customer to update
     * @throws SQLException these errors are handled by the caller
     */
    public static void update(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getAddress());
        preparedStatement.setString(3, customer.getPostalCode());
        preparedStatement.setString(4, customer.getPhoneNumber());
        preparedStatement.setInt(5, customer.getDivisionId());
        preparedStatement.setInt(6, customer.getId());
        preparedStatement.executeUpdate();
    }

    /**
     * Queries the database to delete the passed customer.
     * @param customer the customer to delete
     * @throws SQLException these errors are handled by the caller
     */
    public static void delete(Customer customer) throws SQLException {
        String sqlDeleteAppointments = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sqlDeleteCustomer = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement preparedStatementToDeleteAppointments = JDBC.getConnection().prepareStatement(sqlDeleteAppointments);
        PreparedStatement preparedStatementToDeleteCustomer = JDBC.getConnection().prepareStatement(sqlDeleteCustomer);
        preparedStatementToDeleteAppointments.setInt(1, customer.getId());
        preparedStatementToDeleteCustomer.setInt(1, customer.getId());
        preparedStatementToDeleteAppointments.executeUpdate();
        preparedStatementToDeleteCustomer.executeUpdate();
    }
}
