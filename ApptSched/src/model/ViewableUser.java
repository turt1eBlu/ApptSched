
package model;

/**
 * Model class for representing row data from users database table.
 * 
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ViewableUser {
    private final int UserId;
    private final String UserName;

    /**
     * Constructor for the ViewableUser class.
     * @param UserId  the User ID number
     * @param UserName the User name
     */
    public ViewableUser(int UserId, String UserName) {
        this.UserId = UserId;
        this.UserName = UserName;
    }

    /**
     * Returns the user id number
     * @return user id number
     */
    public int getUserId() {
        return UserId;
    }

    /**
     * Returns the user name.
     * @return user name
     */
    public String getUserName() {
        return UserName;
    }
    
    /**
     * Overrides the default object toString method and returns string representation 
     * of the user as the User ID number and name.
     * 
     * @return formatted string representation of the user
     */
    @Override
    public String toString(){
        String str = UserId + ": " + UserName;
        return str;
    }
    
}
