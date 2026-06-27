package model;

/**
 * Class for modeling appointment time distribution.
 */
@SuppressWarnings("ClassCanBeRecord")
public class DistributionReport {

    private final int count;
    private final int length;

    /**
     * Constructor.
     * @param count the number of appointments
     * @param length the length of the appointments
     */
    public DistributionReport(int count, int length) {
        this.count = count;
        this.length = length;
    }

    /**
     * Getter for the number of appointments.
     * @return the number of appointments
     */
    public int getCount() {
        return count;
    }

    /**
     * Getter for the length of the appointments.
     * @return the length of the appointments
     */
    public int getLength() {
        return length;
    }
}
