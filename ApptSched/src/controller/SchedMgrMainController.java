
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import utils.CurrUser;
import utils.GUI;
import utils.logFileWriter;

/**
 * FXML Controller class for the SchedMgrMain FXML page
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class SchedMgrMainController implements Initializable {

    GUI gui = new GUI();

    /**
     * Loads the Appointment View Screen.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLoadAppointmentScreen(ActionEvent event) {
        gui.loadScreen("/view/ApptViewScreen.fxml", event);
    }

    /**
     * Loads the Appointments by Type Screen.
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLoadApptTypeScreen(ActionEvent event) {
        gui.loadScreen("/view/ApptTypeScreen.fxml", event);
    }

    /**
     * Loads the Contact Schedule Report Screen.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLoadContSchedScreen(ActionEvent event) {
        gui.loadScreen("/view/ContSchedScreen.fxml", event);
    }

    /**
     * Loads the Customer Schedule Report Screen.
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLoadCustSched(ActionEvent event) {
        gui.loadScreen("/view/CustSched.fxml", event);
    }

    /**
     * Loads the Customer View Screen
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLoadCustomerScreen(ActionEvent event) {
        gui.loadScreen("/view/CustViewScreen.fxml", event);
    }

    /**
     * Returns the user to the Login Screen.
     * Writes logout information to activity log.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLogOut(ActionEvent event) {
        //write log out information to login activity file
        logFileWriter.writeLogoff(CurrUser.getName());
        CurrUser.logOff();
        gui.loadScreen("/view/LoginScreen.fxml", event);

    }
    
    /**
     * Initializes the controller class.
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
