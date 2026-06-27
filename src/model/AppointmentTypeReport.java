package model;

/**
 * Class for modeling appointments by type and month.
 */
public class AppointmentTypeReport {

    private final String appointmentType;
    private final int count;

    /**
     * Constructor.
     * 
     * @param appointmentType the appointment type
     * @param count           the number of appointments of the passed type
     */
    public AppointmentTypeReport(String appointmentType, int count) {
        this.appointmentType = appointmentType;
        this.count = count;
    }

    /**
     * Getter for appointment type.
     * 
     * @return appointment type
     */
    // @SuppressWarnings("unused")
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Getter for the number of appointments of the passed type.
     * 
     * @return the number of appointments of the passed type
     */
    public int getCount() {
        return count;
    }
}
