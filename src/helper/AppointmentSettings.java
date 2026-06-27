package helper;

import model.Appointment;
import model.Customer;

/**
 * Static class used for storing information between different views.
 */
public abstract class AppointmentSettings {

    private static final String cssStyles = "-fx-background-color: yellow;";
    private static Appointment appointment;
    private static Customer customer;
    private static String userNameAndId;

    /**
     * Getter for CSS used to report invalid entries on forms.
     * @return string with css
     */
    public static String getCssStyles() {
        return cssStyles;
    }

    /**
     * Getter for the stored selected appointment.
     * @return the selected appointment
     */
    public static Appointment getAppointment() {
        return appointment;
    }

    /**
     * Setter for the stored selected appointment.
     * @param appointment the selected appointment
     */
    public static void setAppointment(Appointment appointment) {
        AppointmentSettings.appointment = appointment;
    }

    /**
     * Getter for the stored selected customer.
     * @return the selected customer
     */
    public static Customer getCustomer() {
        return customer;
    }

    /**
     * Setter for the stored selected customer.
     * @param customer the selected customer
     */
    public static void setCustomer(Customer customer) {
        AppointmentSettings.customer = customer;
    }

    /**
     * Getter for a string with a formatted username and user ID.
     * @return a string formatted as username[ID]
     */
    public static String getUserNameAndId() {
        return userNameAndId;
    }

    /**
     * Setter for a string with a formatted username and user ID.
     * @param userNameAndId a string formatted as username[ID]
     */
    public static void setUserNameAndId(String userNameAndId) {
        AppointmentSettings.userNameAndId = userNameAndId;
    }
}
