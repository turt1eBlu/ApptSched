
package model;

/**
 * Model class for representing row data from contacts database table.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class Contact {
    
    private final int ContactId;
    private final String ContactName;
    private final String Email;

    /**
     * Constructor for the Contact class.
     * @param ContactId Contact ID number
     * @param ContactName Contact Name
     * @param Email Contact Email address
     */
    public Contact(int ContactId, String ContactName, String Email) {
        this.ContactId = ContactId;
        this.ContactName = ContactName;
        this.Email = Email;
    }

    /**
     * Getter for the Contact ID number.
     * @return the Contact ID number
     */
    public int getContactId() {
        return ContactId;
    }

    /**
     * Getter for the Contact Name.
     * @return the Contact Name
     */
    public String getContactName() {
        return ContactName;
    }

    /**
     * Getter for the Contact Email address.
     * @return the email address of the contact
     */
    public String getEmail() {
        return Email;
    }
    
    /**
     * Overrides the default object toString method and returns string representation 
     * of the contact with Contact ID number and Contact name.
     * 
     * @return formatted string representation of the contact
     */
    @Override
    public String toString(){
        return ContactId + ": " +ContactName;
    }
}
