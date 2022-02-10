
package controller;

import dao.dao_country;
import dao.dao_customer;
import dao.dao_firstLevelDivision;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.ViewableCustomer;
import utils.CurrUser;
import utils.GUI;
import utils.MessageDisplay;

/**
 * FXML Controller class for the CustEditScreen FXML page.
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class CustEditScreenController implements Initializable {
    
    GUI gui = new GUI();
    private static ViewableCustomer selectedCustomer;
    private boolean modify = false;
    
    @FXML
    private TextField CustIdTxt;

    @FXML
    private TextField CustNameTxt;

    @FXML
    private TextField AddressTxt;

    @FXML
    private ComboBox<FirstLevelDivision> StateCBox;

    @FXML
    private TextField PostCodeTxt;

    @FXML
    private ComboBox<Country> CountryCBox;

    @FXML
    private TextField PhoneTxt;

    /**
     * Returns the user to the CustViewScreen without saving any changes.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionCancel(ActionEvent event)  {
        gui.loadScreen("/view/CustViewScreen.fxml", event);
    }

    /**
     * Resets fields on customer edit form to initial values.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionResetForm(ActionEvent event) {
        if (modify)
            gui.loadScreen("/view/CustEditScreen.fxml", event, selectedCustomer);
        else
            gui.loadScreen("/view/CustEditScreen.fxml", event);
    }

    /**
     * Retrieves user data from form and sends customer data to be saved to the 
     * database.
     * Popup warning windows are used to inform users of invalid or missing data.
     * All fields must contain data.
     * 
     * @param event OnButtonClick event
     */
    @FXML
    void OnActionSave(ActionEvent event) {
        
        //create customer from fields on form
        String CustomerName = CustNameTxt.getText();
        String Address = AddressTxt.getText();
        int DivisionId = StateCBox.getValue().getDivisionId();
        String PostalCode = PostCodeTxt.getText();
        String Phone = PhoneTxt.getText();
        String UpdatedBy = CurrUser.getName();
        ZonedDateTime LastUpdate = ZonedDateTime.now();
        
        //validate input
        if(CustomerName.isEmpty())
            MessageDisplay.displayWarning("A customer name must be specified!");
        else if (Address.isEmpty())
            MessageDisplay.displayWarning("An address must be specified!");
        else if (PostalCode.isEmpty())
            MessageDisplay.displayWarning("A postal code must be specified!");
        else if (Phone.isEmpty())
            MessageDisplay.displayWarning("An phone number must be specified!");
        else{
            //editing an existing customer
            if(modify){
                int CustomerId = Integer.parseInt(CustIdTxt.getText());
                Customer cust = new Customer(CustomerId, CustomerName, Address, 
                DivisionId, PostalCode, Phone, UpdatedBy, LastUpdate);
                boolean update = dao_customer.modifyCustomer(cust);
                    if (update)
                        MessageDisplay.displayMessage("Customer Update Successful!");
            }
            //creating a new customer
            else{
                String CreatedBy = CurrUser.getName();
                Customer cust = new Customer(CustomerName, Address, DivisionId, PostalCode, 
                        Phone, CreatedBy, UpdatedBy);
                boolean add = dao_customer.addCustomer(cust);
                if (add)
                        MessageDisplay.displayMessage("Customer Added Successfully!");
            }
            this.modify = false;
            gui.loadScreen("/view/CustViewScreen.fxml", event);
        }  
    }
    
    /**
     * Populates the First Level Divisions in the Division combo box corresponding
     * to the country chosen in the Country combo box.
     * 
     * @param event choosing a country in the combo box
     */
    @FXML
    void OnActionPopulateDivision(ActionEvent event) {
        int selCtry = CountryCBox.getSelectionModel().getSelectedItem().getCountryId();
        StateCBox.setItems(dao_firstLevelDivision.getDivisions(selCtry));
        StateCBox.getSelectionModel().selectFirst();
    }
    
    /**
     * Pre-fills fields on form with data from selected customer when the edit 
     * option is chosen.
     * 
     * @param customer the selected customer sent to the screen
     */
    public void sendCustomer(ViewableCustomer customer) {
        selectedCustomer = customer;
        
        CustIdTxt.setText(String.valueOf(selectedCustomer.getCustomerId()));
        CustNameTxt.setText(selectedCustomer.getCustomerName());
        AddressTxt.setText(selectedCustomer.getAddress());
        Country selectedCountry = dao_country.searchCountry(selectedCustomer.getCountry());
        CountryCBox.setValue(selectedCountry);
        FirstLevelDivision selectedDivision = dao_firstLevelDivision.searchDivision(selectedCustomer.getDivision());
        StateCBox.setValue(selectedDivision);
        PostCodeTxt.setText(selectedCustomer.getPostalCode());
        PhoneTxt.setText(selectedCustomer.getPhone());
        this.modify = true;
        
    }
    /**
     * Initializes the controller class.
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountryCBox.setItems(dao_country.getAllCountries());
        CountryCBox.getSelectionModel().selectFirst();
        int selCtry = CountryCBox.getSelectionModel().getSelectedItem().getCountryId();
        StateCBox.setItems(dao_firstLevelDivision.getDivisions(selCtry));
        StateCBox.getSelectionModel().selectFirst();
    }    
    
}
