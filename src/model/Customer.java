package model;

/**
 * Class used for modeling customers.
 */
public class Customer {

    private int id;
    private final String name;
    private final String address;
    private final String postalCode;
    private final String phoneNumber;
    private final int divisionId;
    private final String division;
    private final String country;

    /**
     * Constructor used to get customers from the database.
     * @param id the ID
     * @param name the name
     * @param address the address
     * @param postalCode the postal code
     * @param phoneNumber the phone number
     * @param divisionId the division ID
     * @param division the division
     * @param country the country
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /**
     * Constructor used to add new customers to the database (ID is auto-generated).
     * @param name the name
     * @param address the address
     * @param postalCode the postal code
     * @param phoneNumber the phone number
     * @param divisionId the division ID
     * @param division the division
     * @param country the country
     */
    public Customer(String name, String address, String postalCode, String phoneNumber,int divisionId, String division, String country) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /**
     * Getter for ID.
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for address.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for postal code.
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Getter for phone number.
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for division ID.
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Getter for division.
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for country.
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Overrides the toString method so these objects are formatted properly in combo boxes.
     * @return the formatted string
     */
    @Override
    public String toString() {
        return getName() + " [" + getId() + "]";
    }
}