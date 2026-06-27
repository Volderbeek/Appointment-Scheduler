package model;

/**
 * Class for modeling contacts.
 */
@SuppressWarnings("ALL")
public class Contact {

    private final int id;
    private final String name;
    private final String email;

    /**
     * Constructor.
     * 
     * @param id    the ID
     * @param name  the name
     * @param email the e-mail
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
     * Getter for e-mail.
     * <p>
     * <p>
     * </p>
     * <b>NOTES:</b> This is not actually used in this project but left in for
     * future changes/improvements.
     * </p>
     *
     * @return e-mail
     */
    // @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    /**
     * Overrides the toString method so these objects are formatted properly in
     * combo boxes.
     * 
     * @return the formatted string
     */
    @Override
    public String toString() {
        return getName() + " [" + getId() + "]";
    }
}
