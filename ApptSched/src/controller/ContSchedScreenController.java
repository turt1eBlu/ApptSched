
package controller;

import dao.dao_appointment;
import dao.dao_contact;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import utils.DBConnection;
import utils.DBQuery;
import utils.GUI;

/**
 * FXML Controller class for the ContSchedScreen FXML page.
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ContSchedScreenController implements Initializable {
    
    GUI gui = new GUI();
    
    @FXML
    private ComboBox<Contact> ContactCBox;

    @FXML
    private TableView<Appointment> ApptTbl;

    @FXML
    private TableColumn<Appointment, Integer> ApptIdCol;

    @FXML
    private TableColumn<Appointment, String> ApptTitleCol;

    @FXML
    private TableColumn<Appointment, String> ApptDescCol;

    @FXML
    private TableColumn<Appointment, String> ApptLocCol;

    @FXML
    private TableColumn<Appointment, Integer> ApptContCol;

    @FXML
    private TableColumn<Appointment, String> ApptTypeCol;

    @FXML
    private TableColumn<Appointment, String> ApptStartCol;

    @FXML
    private TableColumn<Appointment, String> ApptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> ApptCustIdCol;

    @FXML
    private Button ReturnBtn;
    
    /**
     * Calls the filterAppts method when contact is chosen.
     * 
     * @param event User chooses a contact from the combo box control
     */
    @FXML
    void OnActionFilterAppts(ActionEvent event) {
        filterAppts();
    }
    
    /**
    * Returns user to the SchedMgrMain Screen.
    * 
    * @param event OnButtonClick Event
    */
    @FXML
    void OnActionReturnToMain(ActionEvent event) {
        gui.loadScreen("/view/SchedMgrMain.fxml", event);
    }
    
    /**
    * Refreshes the table view to display updated data.
    */
    private void updateTable(){
        ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        ApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        ApptDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ApptLocCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        ApptContCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        ApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ApptStartCol.setCellValueFactory(new PropertyValueFactory<>("StartString"));
        ApptEndCol.setCellValueFactory(new PropertyValueFactory<>("EndString"));
        ApptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
    }

    /**
     * Filters appointments by selected contact.
     */
    private void filterAppts(){
        String name = ContactCBox.getSelectionModel().getSelectedItem().getContactName();
        String input ="SELECT * FROM appointments WHERE Contact_ID = (SELECT "
                + "Contact_ID FROM contacts WHERE Contact_Name = ?)";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
        PreparedStatement ps =DBQuery.getPreparedStatement();
        try {
            ps.setString(1, name);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        ApptTbl.setItems(dao_appointment.getAppointments(ps));
        updateTable(); 
    }
    
    /**
     * Initializes the controller class.
     * 
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ContactCBox.setItems(dao_contact.getAllContacts());
        ContactCBox.getSelectionModel().selectFirst();
        filterAppts();
    }    
    
}
