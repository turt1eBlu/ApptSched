
package controller;

import dao.dao_appointment;
import dao.dao_user;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import utils.CurrUser;
import utils.GUI;
import utils.MessageDisplay;
import utils.logFileWriter;

/**
 * FXML Controller class for the LoginScreen FXML page
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class LoginScreenController implements Initializable {
    
    GUI gui = new GUI();
    
    @FXML
    private Label TitleLbl;

    @FXML
    private Label UNameLbl;
        
    @FXML
    private Label PswdLbl;
    
    @FXML
    private TextField UserNameTxt;

    @FXML
    private TextField PasswordFld;

    @FXML
    private Button ResetBtn;

    @FXML
    private Button LoginBtn;
        
    @FXML
    private Label RegionLbl;
        
    @FXML
    private Label RegLbl;

    
    ResourceBundle rb;
    String warning;

    /**
     * Allows valid users access to the program.
     * Writes login attempts to login tracking file.  Alerts user if there is
     * an upcoming appointment within 15 minutes.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionLogin(ActionEvent event) { 
        
        String user = UserNameTxt.getText();
        String pswd = PasswordFld.getText();

        if (dao_user.validateUser(user, pswd)){
            //log successful attempt
            logFileWriter.writeLogin(user);
            //set user as Current User
            CurrUser.setName(user);
            //check for appointments within 15 min
            dao_appointment.getAlert(user);
            //load main screen
            gui.loadScreen("/view/SchedMgrMain.fxml", event);
            
        }
        else{
            //log failed attempt
            logFileWriter.writeAttempt(user);
            MessageDisplay.displayWarning(warning);
        }
    }
    
    /**
     * Clears text in text fields
     * @param event onButtonClick Event
     */
    @FXML
    void OnActionResetTextFields(ActionEvent event) {
        UserNameTxt.clear();
        PasswordFld.clear();
    }
    
    /**
     * Initializes the controller class.
     * @param url unused
     * @param rb The resource bundle for the system default locale
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //set resource bundle
        this.rb = rb;

        //Change display label text for locale
        TitleLbl.setText(rb.getString("title"));
        UNameLbl.setText(rb.getString("User"));
        PswdLbl.setText(rb.getString("Password"));
        ResetBtn.setText(rb.getString("Reset"));
        LoginBtn.setText(rb.getString("Login"));
        RegLbl.setText(rb.getString("region"));
        RegionLbl.setText(ZoneId.systemDefault().toString());
        warning=rb.getString("warning");
       
    }    
    
}
