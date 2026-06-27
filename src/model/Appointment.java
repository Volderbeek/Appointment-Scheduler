package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class for modeling appointments.
 */
public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalDate endDate;
    private final LocalTime endTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor used to get appointments from the database.
     * @param id the ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param contact the contact name
     * @param type the type
     * @param startDate the start date
     * @param startTime the start time
     * @param endDate the end date
     * @param endTime the end time
     * @param customerID the customer ID
     * @param userID the user ID
     * @param contactID the contact ID
     */
    public Appointment(int id, String title, String description, String location, String contact, String type, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor used to add appointments to the database (has no ID).
     * @param title the title
     * @param description the description
     * @param location the location
     * @param contact the contact name
     * @param type the type
     * @param start the start date and time
     * @param end the end date and time
     * @param customerID the customer ID
     * @param userID the user ID
     * @param contactID the contact ID
     */
    public Appointment(String title, String description, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.endDate = end.toLocalDate();
        this.endTime = end.toLocalTime();
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor used to update appointments in the database (takes date-times for convenience).
     * <p><p></p>
     * <b>NOTES:</b> This is, admittedly, a bit redundant, but I'm going to leave it here for now.
     * </p>
     * @param id the ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param contact the contact name
     * @param type the type
     * @param start the start date and time
     * @param end the end date and time
     * @param customerID the customer ID
     * @param userID the user ID
     * @param contactID the contactID
     */
    public Appointment(int id, String title, String description, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.endDate = end.toLocalDate();
        this.endTime = end.toLocalTime();
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor used when getting appointments by type (query does not join with contact table).
     * @param id the ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start date and time
     * @param end the end date and time
     * @param customerID the customer ID
     * @param userID the user ID
     * @param contactID the contact ID
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.endDate = end.toLocalDate();
        this.endTime = end.toLocalTime();
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor used to check upcoming appointments.
     * @param id the ID
     * @param start the start date and time
     * @param end the end date and time
     */
    public Appointment(int id, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.startDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.endDate = end.toLocalDate();
        this.endTime = end.toLocalTime();
    }

    /**
     * Getter for ID.
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for location.
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for contact name.
     * @return contact name
     */
    public String getContact() {
        return contact;
    }

    /**
     * Getter for type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for start date.
     * @return start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Getter for start time.
     * @return start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for end date.
     * @return end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Getter for end time.
     * @return end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for customer ID.
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Getter for user ID.
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for contact ID.
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Appointment appointment = (Appointment) obj;
        return this.getCustomerID() == appointment.getCustomerID() && this.userID == appointment.getUserID();
    }
}