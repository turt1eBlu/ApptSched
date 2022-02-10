
package utils;

import javafx.scene.control.Alert;

/**
 * Utility class for creating pop-up message display windows.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class MessageDisplay {
    
    /**
     * Displays a popup warning box to display a message to the user
     * @param message a string warning message to display 
     */ 
    public static void displayWarning(String message)
    {   
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Displays a popup information box to display a message to the user
     * @param message a string information message to display 
     */ 
    public static void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
