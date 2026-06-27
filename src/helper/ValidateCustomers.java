package helper;

/**
 * Static class used for validation of customer forms.
 */
public class ValidateCustomers {

    private static boolean isValid = true;
    private static StringBuilder errorMessages = new StringBuilder();

    /**
     * Validates name field.
     * @param name string to check
     * @return true if valid
     */
    public static boolean checkName(String name) {
        boolean check = !name.isEmpty();
        if (!check) {
            errorMessages.append("Name is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates address field.
     * @param address string to check
     * @return true if valid
     */
    public static boolean checkAddress(String address) {
        boolean check = !address.isEmpty();
        if (!check) {
            errorMessages.append("Address is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates postal code field.
     * @param postalCode string to check
     * @return true if valid
     */
    public static boolean checkPostalCode(String postalCode) {
        boolean check = !postalCode.isEmpty();
        if (!check) {
            errorMessages.append("Postal Code is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates phone number field.
     * @param phoneNumber string to check
     * @return true if valid
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        boolean check = !phoneNumber.isEmpty();
        if (!check) {
            errorMessages.append("Phone Number is empty.\n\n");
            isValid = false;
        }
        return !check;
    }

    /**
     * Validates division combo box.
     * @param division string to check
     * @return true if valid
     */
    public static boolean checkDivision(String division) {
        boolean check = !division.isEmpty();
        if (!check) {
            errorMessages.append("Division is not selected.\n\n");
            isValid = false;
        }
        return !check;
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
}
