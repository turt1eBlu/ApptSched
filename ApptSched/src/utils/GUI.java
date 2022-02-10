
package utils;

import controller.ApptEditScreenController;
import controller.CustEditScreenController;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;
import model.ViewableCustomer;

/**
 * Utility class for creating and loading FXML windows. 
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class GUI {
    Stage stage;
    Parent scene;
    CustEditScreenController cesControl;
    ApptEditScreenController aesControl;
    ResourceBundle rb = ResourceBundle.getBundle("utils/lang", Locale.getDefault());
    
    /**
     * Overloaded method to create and load an FXML window that also loads a 
     * ViewableCustomer object.
     * @param screenName the name of the FXML page
     * @param event OnButtonClick Event
     * @param cust customer to be edited
     */
    public void loadScreen(String screenName, ActionEvent event, ViewableCustomer cust){
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(screenName));
            loader.setResources(rb);
            scene = loader.load();
            cesControl = loader.getController();
            cesControl.sendCustomer(cust);
            stage.setScene(new Scene(scene));
            stage.show();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Overloaded method to create and load an FXML window that also loads an 
     * Appointment object.
     * @param screenName the name of the FXML page
     * @param event OnButtonClick Event
     * @param appt appointment to be edited
     *
     */
    public void loadScreen(String screenName, ActionEvent event, Appointment appt){
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(screenName));
            loader.setResources(rb);
            scene = loader.load();
            aesControl = loader.getController();
            aesControl.sendAppointment(appt);
            stage.setScene(new Scene(scene));
            stage.show();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Overloaded method to create and load an FXML window.
     * @param screenName the name of the FXML page
     * @param event OnButtonClick Event
     */
    public void loadScreen(String screenName, ActionEvent event){
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(screenName));
            loader.setResources(rb);
            scene = loader.load();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
