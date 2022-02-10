
package controller;

import dao.dao_customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ViewableCustomer;
import utils.GUI;
import utils.MessageDisplay;

/**
 * FXML Controller class for the CustViewScreen FXML page
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class CustViewScreenController implements Initializable {
    
    GUI gui = new GUI();

    @FXML
    private TableView<ViewableCustomer> CustTbl;

    @FXML
    private TableColumn<ViewableCustomer, Integer> CustIdCol;

    @FXML
    private TableColumn<ViewableCustomer, String> CustNameCol;

    @FXML
    private TableColumn<ViewableCustomer, String> CustAddrCol;

    @FXML
    private TableColumn<ViewableCustomer, String> StateCol;

    @FXML
    private TableColumn<ViewableCustomer, String> PostCodeCol;

    @FXML
    private TableColumn<ViewableCustomer, String> CountryCol;

    @FXML
    private TableColumn<ViewableCustomer, String> PhoneCol;

    /**
     * Loads the CustEditScreen with no pre-filled data so users can create a new 
     * customer.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionAddCustomer(ActionEvent event){
        gui.loadScreen("/view/CustEditScreen.fxml", event);
    }

    /**
     * Calls the dao_customer deleteCustomer method to remove the selected 
     * appointment from the database.
     * Customers cannot be deleted if they have existing appointments.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionDeleteCust(ActionEvent event) {
        ViewableCustomer selCust = CustTbl.getSelectionModel().getSelectedItem();
        try{
            boolean success = dao_customer.deleteCustomer(selCust);
            /*if (!success){
                String message = "Customer could not be deleted.  Delete all appointments"
                        + "for customer first.  Then try again.";
                MessageDisplay.displayWarning(message);
            } */
            gui.loadScreen("/view/CustViewScreen.fxml", event);
        } catch(Exception e){
            MessageDisplay.displayWarning("You must choose a Customer first!");
        }
    }

    /**
     * Loads the CustEditScreen with pre-filled data so users can edit the selected 
     * customer.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionEditCustomer(ActionEvent event) {
        //send cust info to new screen
        ViewableCustomer selCust = CustTbl.getSelectionModel().getSelectedItem();
        try{
            gui.loadScreen("/view/CustEditScreen.fxml", event, selCust);
        } catch(Exception e){
            MessageDisplay.displayWarning("You must choose a Customer first!");
        }
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
     * Initializes the controller class.
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CustTbl.setItems(dao_customer.getCustomers());    
        CustIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        CustNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        CustAddrCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        StateCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
        PostCodeCol.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        CustTbl.getSortOrder().addAll(CustIdCol);
    }    
    
}
