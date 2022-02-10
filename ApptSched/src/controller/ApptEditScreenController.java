
package controller;

import dao.dao_appointment;
import dao.dao_contact;
import dao.dao_customer;
import dao.dao_user;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.ViewableCustomer;
import model.ViewableUser;
import utils.CurrUser;
import utils.GUI;
import utils.MessageDisplay;
import utils.TimeHandler;

/**
 * FXML Controller class for the ApptEditScreen FXML page.
 * 
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ApptEditScreenController implements Initializable {
    
    GUI gui = new GUI();
    private static Appointment selectedAppointment;
    private boolean modify = false;
    
    @FXML
    private TextField ApptIDTxt;

    @FXML
    private ComboBox<ViewableCustomer> CustCBox;

    @FXML
    private TextField TitleTxt;

    @FXML
    private TextField DescTxt;

    @FXML
    private TextField TypeTxt;

    @FXML
    private TextField LocationTxt;
        
    @FXML
    private ComboBox<ViewableUser> UserCBox;

    @FXML
    private ComboBox<Contact> ContactCBox;

    @FXML
    private ComboBox<LocalTime> StartTimeCBox;

    @FXML
    private DatePicker StartDatePkr;

    @FXML
    private ComboBox<LocalTime> EndTimeCBox;

    @FXML
    private DatePicker EndDatePkr;

    /**
     * Returns to the Appointment View Screen.
     * 
     * @param event OnButtonClick event
     */
    @FXML
    void OnActionCancel(ActionEvent event){
        this.modify = false;
        gui.loadScreen("/view/ApptViewScreen.fxml", event);
    }

    /**
     * Resets fields on appointment edit form to their initial values.
     * 
     * @param event OnButtonClick event
     */
    @FXML
    void OnActionResetForm(ActionEvent event) {
        if (modify)
            gui.loadScreen("/view/ApptEditScreen.fxml", event, selectedAppointment);
        else
            gui.loadScreen("/view/ApptEditScreen.fxml", event);
    }

    /**
     * Retrieves user data from form and sends appointment data to be saved to the database.
     * Popup warning windows are used to inform users of invalid or missing data. Title and
     * description fields are allowed to be blank; all others must contain data.
     * 
     * @param event OnButtonClick event
     */
    @FXML
    void OnActionSave(ActionEvent event)  {
        //create appointment from field values on form       
        String Title = TitleTxt.getText();
        String Description = DescTxt.getText();
        String Location = LocationTxt.getText();
        String Type = TypeTxt.getText();
        LocalDate StartDate = StartDatePkr.getValue();
        LocalTime StartTime = StartTimeCBox.getValue();
        ZonedDateTime Start = ZonedDateTime.of(StartDate, StartTime, ZoneId.systemDefault());
        LocalDate EndDate = EndDatePkr.getValue();
        LocalTime EndTime = EndTimeCBox.getValue();
        ZonedDateTime End = ZonedDateTime.of(EndDate, EndTime, ZoneId.systemDefault());
        String UpdatedBy = CurrUser.getName();
        ZonedDateTime LastUpdate = ZonedDateTime.now();
        int CustomerID = CustCBox.getValue().getCustomerId();
        int UserID = UserCBox.getValue().getUserId();
        int ContactID = ContactCBox.getValue().getContactId();
        int AppointmentId = -1;
        if (modify)
            AppointmentId = Integer.parseInt(ApptIDTxt.getText());
        //validate appointment time input
        if (End.isBefore(Start) ||  End.isEqual(Start))
            MessageDisplay.displayWarning("Ending time must be after start time.");
        else if(dao_appointment.checkOverlap(Start, End, CustomerID, AppointmentId))
            MessageDisplay.displayWarning("Chosen appointment time conflicts with existing customer appointment.");
        else if(!TimeHandler.withinOfficeHours(Start, End))
            MessageDisplay.displayWarning("Chosen appointment time is not during office hours (8am-10pm EST)");
        //make sure fields are not empty (desc & title can be empty string "")
        else if (Location.isEmpty())
            MessageDisplay.displayWarning("A Location must be specified!");
        else if (Type.isEmpty())
            MessageDisplay.displayWarning("A Type must be specified!");
        else{    
            if(modify){
                
                Appointment appointment  = new Appointment(AppointmentId, Title, Description, 
                    Location, Type, Start, End, UpdatedBy, LastUpdate, CustomerID, 
                    UserID, ContactID);
                boolean update = dao_appointment.modifyAppointment(appointment);
                if (update)
                    MessageDisplay.displayMessage("Appointment Update Successful!");
            }
            else{
                Appointment appointment = new Appointment(Title, Description, 
                Location, Type, Start, End, CustomerID, UserID, ContactID);
                boolean add = dao_appointment.addAppointment(appointment);
                if (add)
                    MessageDisplay.displayMessage("Appointment Added Successfully!");
            }
            this.modify = false;
            gui.loadScreen("/view/ApptViewScreen.fxml", event);
        }
    }

    /**
     * Changes the date in the appointment end date box to match the chosen start
     * date.
     * @param event Date chosen in DatePicker control
     */
    @FXML
    void OnActionUpdateEndDate(ActionEvent event) {
        EndDatePkr.setValue(StartDatePkr.getValue());
    }

    /**
     * Changes the time in the appointment end time combo box to half hour after
     * the time chosen in the start time combo box.
     * 
     * @param event time chosen in appointment start time combo box
     */
    @FXML
    void OnActionUpdateEndTime(ActionEvent event) {
        EndTimeCBox.getSelectionModel().selectFirst();
        LocalTime startTime = StartTimeCBox.getSelectionModel().getSelectedItem();
        while (!EndTimeCBox.getSelectionModel().getSelectedItem().isAfter(startTime))
            EndTimeCBox.getSelectionModel().selectNext();
    }
    
    /**
     * Pre-fills fields on form with data from selected appointment when the edit 
     * option is chosen.
     * 
     * @param appt the selected appointment sent to the screen
     */
    public void sendAppointment(Appointment appt){
        selectedAppointment = appt;
        ApptIDTxt.setText(String.valueOf(selectedAppointment.getAppointmentId()));
        ViewableCustomer selCust = dao_customer.getViewCust(selectedAppointment.getCustomerID());
        CustCBox.setValue(selCust);
        TitleTxt.setText(selectedAppointment.getTitle());
        DescTxt.setText(selectedAppointment.getDescription());
        TypeTxt.setText(selectedAppointment.getType());
        LocationTxt.setText(selectedAppointment.getLocation());
        Contact selectedContact = dao_contact.searchContact(selectedAppointment.getContactID());;
        ContactCBox.setValue(selectedContact);
        UserCBox.setValue(dao_user.getViewUser(selectedAppointment.getUserID()));
        StartTimeCBox.setValue(selectedAppointment.getStart().toLocalTime());
        StartDatePkr.setValue(selectedAppointment.getStart().toLocalDate());
        EndTimeCBox.setValue(selectedAppointment.getEnd().toLocalTime());
        EndDatePkr.setValue(selectedAppointment.getEnd().toLocalDate());
        this.modify = true;
    }
    
    /**
     * Initializes the controller class.
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //put all contacts in combo box and select first one
        ContactCBox.setItems(dao_contact.getAllContacts());
        ContactCBox.getSelectionModel().selectFirst();
        
        //put all users in combo box and select first one
        UserCBox.setItems(dao_user.getAllUserNames());
        UserCBox.getSelectionModel().selectFirst();
        
        //put all customers in combo box and select first one
        CustCBox.setItems(dao_customer.getCustomers());
        CustCBox.getSelectionModel().selectFirst();
        
        //set start dates to current date
        StartDatePkr.setValue(LocalDate.now());
        EndDatePkr.setValue(LocalDate.now());
        
        //add localtime values to time combo boxes in half hour increments
        LocalTime lt, lt30;
        for(int i = 0; i < 24; i++){
            lt = LocalTime.of(i, 0);
            lt30 = LocalTime.of(i, 30);
            StartTimeCBox.getItems().add(lt);
            StartTimeCBox.getItems().add(lt30);
            EndTimeCBox.getItems().add(lt);
            EndTimeCBox.getItems().add(lt30);
        }

        //set default start time to next half hour from now
        LocalTime startValue = LocalTime.now();
        StartTimeCBox.getSelectionModel().selectFirst();
        while (StartTimeCBox.getSelectionModel().getSelectedItem().isBefore(startValue))
            StartTimeCBox.getSelectionModel().selectNext();
        
        //set default end time to next half hour past start time
        EndTimeCBox.getSelectionModel().selectFirst();
        LocalTime startTime = StartTimeCBox.getSelectionModel().getSelectedItem();
        while (!EndTimeCBox.getSelectionModel().getSelectedItem().isAfter(startTime))
            EndTimeCBox.getSelectionModel().selectNext();  
    }    
    
}
