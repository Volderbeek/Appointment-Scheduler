package model;

@SuppressWarnings("ClassCanBeRecord")
public class Division {

    private final int id;
    private final String name;
    private final int countryId;
    private final String countryName;

    /**
     * Constructor.
     * 
     * @param id          the ID
     * @param name        the name
     * @param countryId   the country ID
     * @param countryName the country name
     */
    public Division(int id, String name, int countryId, String countryName) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Getter for ID.
     * 
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for country ID.
     * 
     * @return country ID
     */
    // @SuppressWarnings("unused")
    public int getCountryId() {
        return countryId;
    }

    /**
     * Getter for country name.
     * 
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Overrides the toString method so these objects are formatted properly in
     * combo boxes.
     * 
     * @return the formatted string
     */
    @Override
    public String toString() {
        return getName();
    }
}
