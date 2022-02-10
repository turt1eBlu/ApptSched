
package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 * Utility class for writing login information to a file.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class logFileWriter {
    private static final String LOGFILE = "login_activity.txt";
    
    /**
     * Writes a successful login attempt to the file.
     * @param usrName user who logs in
     */
    public static void writeLogin(String usrName){
        writeFile(usrName, "Login");
    }
    
    /**
     * Writes a successful logoff attempt to the file.
     * @param usrName user who logs off
     */
    public static void writeLogoff(String usrName){
        writeFile(usrName, "Logoff");
    }
    
    /**
     * Writes an unsuccessful login attempt to the file.
     * @param usrName user who failed to log in
     */
    public static void writeAttempt(String usrName){
        writeFile(usrName, "Fail");
    }
    
    /**
     * Writes login events to text file.
     * Appends new data to end of file.
     * @param user user name
     * @param type type of login event (Login, Logoff, Fail)
     */
    private static void writeFile(String user, String type){
        String typeString = "Unsuccessful login attempt by user: ";
        if (type.equals("Login"))
            typeString = "Login successful by user: ";
        else if (type.equals("Logoff"))
            typeString = "Logoff successful by user: ";

        try {
            FileWriter fwriter = new FileWriter(LOGFILE, true);
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println(typeString + user + " at " + ZonedDateTime.now());
            outputFile.println();
            fwriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
