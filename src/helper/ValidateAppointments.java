package helper;

import database.AppointmentsQuery;
import model.Contact;
import model.Customer;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Static class used for validation of appointment forms.
 */
public class ValidateAppointments {

    private static boolean isValid = true;
    private static StringBuilder errorMessages = new StringBuilder();
    private static int numberOfDuplicates;

    /**
     * Validates title field.
     * @param title string to check
     * @return true if valid
     */
    public static boolean checkTitle(String title) {
        boolean check = !title.isEmpty();
        if (!check) {
            errorMessages.append("Title is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates description field.
     * @param description string to check
     * @return true if valid
     */
    public static boolean checkDescription(String description) {
        boolean check = !description.isEmpty();
        if (!check) {
            errorMessages.append("Description is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates location field.
     * @param location string to check
     * @return true if valid
     */
    public static boolean checkLocation(String location) {
        boolean check = !location.isEmpty();
        if (!check) {
            errorMessages.append("Location is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates type field.
     * @param type string to check
     * @return true if valid
     */
    public static boolean checkType(String type) {
        boolean check = !type.isEmpty();
        if (!check) {
            errorMessages.append("Type is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates customer combo box.
     * @param customer the customer to check
     * @return true if valid
     */
    public static boolean checkCustomer(Customer customer) {
        boolean check = customer != null;
        if (!check) {
            errorMessages.append("Customer is not selected.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates contact combo box.
     * @param contact the contact to check
     * @return true if valid
     */
    public static boolean checkContact(Contact contact) {
        boolean check = contact != null;
        if (!check) {
            errorMessages.append("Contact is not selected.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates start and end dates and times in various ways.
     * @param startTime the start date and time
     * @param endTime the end date and time
     * @param customerId the customer ID to check overlap with
     * @param userId the user ID to check overlap with
     * @return true if valid
     */
    public static boolean checkTimes(LocalDateTime startTime, LocalDateTime endTime, int customerId, int userId) {
        try {
            boolean check = true;
            if (startTime.isAfter(endTime)) {
                errorMessages.append("Start time is not before end time.\n\n");
                check = false;
                isValid = false;
            }
            Duration appointmentLength = Duration.between(startTime, endTime);
            if (appointmentLength.toMinutes() < 15 || appointmentLength.toMinutes() > 60) {
                errorMessages.append("Appointments must be between 15 minutes to an hour.\n\n");
                check = false;
                isValid = false;
            }
            LocalDateTime startBusinessHours = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), 8, 0);
            LocalDateTime endBusinessHours = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), 22, 0);

            ZonedDateTime zonedStartBusinessHours = startBusinessHours.atZone(ZoneId.of("America/New_York"));
            ZonedDateTime localStartBusinessHours = zonedStartBusinessHours.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime zonedEndBusinessHours = endBusinessHours.atZone(ZoneId.of("America/New_York"));
            ZonedDateTime localEndBusinessHours = zonedEndBusinessHours.withZoneSameInstant(ZoneId.systemDefault());
            LocalDateTime start = localStartBusinessHours.toLocalDateTime();
            LocalDateTime end = localEndBusinessHours.toLocalDateTime();

            if (startTime.isBefore(start) || startTime.isAfter(end) || endTime.isBefore(start) || endTime.isAfter(end)) {
                errorMessages.append("Chosen appointment time is not within the business hours of ")
                        .append(start.toLocalTime())
                        .append(" to ")
                        .append(end.toLocalTime())
                        .append(".\n\n");
                check = false;
                isValid = false;
            }
            if (AppointmentsQuery.checkForDuplicate(startTime, endTime, customerId, userId) > numberOfDuplicates) {
                errorMessages.append("Chosen appointment time is already taken.\n\n");
                check = false;
                isValid = false;
            }
            return !check;
        }
        catch (SQLException exception) {
            errorMessages.append("Database could not be reached.\n\n");
            return true;
        }
    }

    /**
     * Checks if all input is valid.
     * @return true if all input is valid
     */
    public static boolean isValid() {
        return isValid;
    }

    /**
     * Returns a string with all the error messages formatted for an alert to the user.
     * @return string with all the error messages
     */
    public static String getErrorMessages() {
        return errorMessages.toString();
    }

    /**
     * Resets all errors flag and clears error messages. Used when re-submitting form.
     */
    public static void clearErrors() {
        errorMessages = new StringBuilder();
        isValid = true;
    }

    /**
     * Setter for the number of duplicates allowed. Used because updates will always have 1 duplicate.
     * @param numberOfDuplicates the number of duplicates allowed
     */
    public static void setNumberOfDuplicates(int numberOfDuplicates) {
        ValidateAppointments.numberOfDuplicates = numberOfDuplicates;
    }
}
