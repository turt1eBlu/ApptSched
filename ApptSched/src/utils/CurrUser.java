
package utils;

/**
 * Static class for tracking the current user of the program.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class CurrUser {
    private static String Name;
    private static boolean loggedIn;

    /**
     * Constructor for the CurrUser class.
     * @param currUser the name of the user currently logged in to the program
     */
    public CurrUser(String currUser) {
        CurrUser.Name = currUser;
        CurrUser.loggedIn = true;
    }

    /**
     * Returns the name of the user currently logged in to the program.
     * @return name of the user currently logged in to the program
     */
    public static String getName() {
        return Name;
    }

    /**
     * Returns a value showing whether the user is currently logged in.
     * @return the user is currently logged in
     */
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the value of the loggedIn class member.
     * @param log new value for loggedIn
     */
    private static void setLoggedIn(boolean log) {
        CurrUser.loggedIn = log;
    }
    
    /**
     * Sets the loggedIn value to false.
     */
    public static void logOff(){
        setLoggedIn(false);
    }

    /**
     * Setter for the name of the current user.
     * @param Name current user name
     */
    public static void setName(String Name) {
        CurrUser.Name = Name;
    }
    
}
