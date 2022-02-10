
package controller;

import dao.dao_appointment;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.GUI;
import model.Appointment;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.temporal.WeekFields;
import java.util.Locale;
import utils.ApptInterface;
import utils.DBQuery;
import utils.MessageDisplay;

/**
 * FXML Controller class for the ApptViewScreen FXML page.
 * 
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ApptViewScreenController implements Initializable {
    GUI gui = new GUI();
    ApptInterface chooseMonth;
    ApptInterface chooseWeek;
    ApptInterface display;

     
    @FXML
    private RadioButton AllRBtn;
    
    @FXML
    private RadioButton WeekRbtn;

    @FXML
    private ToggleGroup ViewGrp;

    @FXML
    private RadioButton MonthRBtn;

    @FXML
    private DatePicker CalendarPkr;

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
    private Button ApptAddBtn;

    @FXML
    private Button ApptEditBtn;

    @FXML
    private Button ApptDelBtn;

    @FXML
    private Button ReturnBtn;
    
    /**
     * Changes table view to display all data.
     * 
     * @param event All radio button selected event
     */
    @FXML
    void OnActionViewAll(ActionEvent event) {
       viewAll(); 
    }
     
    /**
     * Changes table view to display data for chosen month.
     * 
     * @param event Month radio button selected event
     */
    @FXML
    void OnActionViewMonth(ActionEvent event) {
        chooseMonth.filter();
        updateTable();
    }

    /**
     * Changes table view to display data for chosen week.
     * 
     * @param event Week radio button selected event 
     */
    @FXML
    void OnActionViewWeek(ActionEvent event) {
        chooseWeek.filter();
        updateTable();
    }
    
    /**
     * Changes table view based on date selected.
     * 
     * @param event User chooses a date from the calendar picker control
     */
    @FXML
    void OnActionFilterAppts(ActionEvent event) {
        display.filter();
    }
    
    /**
     * Loads the ApptEditScreen with no pre-filled data so users can create a new 
     * appointment.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionAddAppt(ActionEvent event) {
        gui.loadScreen("/view/ApptEditScreen.fxml", event);
    }

    /**
     * Calls the dao_appointment deleteAppointment method to remove the selected 
     * appointment from the database.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionDeleteAppt(ActionEvent event) {
        Appointment selAppt = ApptTbl.getSelectionModel().getSelectedItem();
        //int selApptID = selAppt.getAppointmentId();
        try{
            boolean success = dao_appointment.deleteAppointment(selAppt);
            /*
            if (!success){
                String message = "Appointment could not be cancelled.";
                MessageDisplay.displayWarning(message);
            }
            else{
                String message = "Appointment #" + selAppt.getAppointmentId() +
                        " Type: " + selAppt.getType() + " has been cancelled.";
                MessageDisplay.displayMessage(message);
            }*/
            display.filter();
        } catch(Exception e){
            MessageDisplay.displayWarning("You must select an appointment first");
        }
        
    }

    /**
     * Loads the ApptEditScreen with pre-filled data so users can edit the selected 
     * appointment.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionEditAppt(ActionEvent event) {
        Appointment selAppt = ApptTbl.getSelectionModel().getSelectedItem();
        try{
            gui.loadScreen("/view/ApptEditScreen.fxml", event, selAppt);
        }catch (Exception e){
            MessageDisplay.displayWarning("You must select an appointment first");
        }
    }

    /**
     * Returns user to the SchedMgrMain Screen.
     * 
     * @param event OnButtonClick Event
     */
    @FXML
    void OnActionReturnToMain(ActionEvent event){
        gui.loadScreen("/view/SchedMgrMain.fxml", event);
    }
    
    /**
     * Refreshes the table view to display updated data.
     * 
     */
    private void updateTable(){
        ApptTbl.getSortOrder().add(ApptIdCol);
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
     * Filters appointments by either week or month depending on the term parameter
     * supplied.
     * If the supplied term is "week" then the value is in the range 1-52 for the 52 weeks in a year.
     * If the supplied term is "month" then the value is int the range of 1-12 for the 12 months.
     * 
     * @param term either "week" or "month"
     * @param value the corresponding term number
     */
    private void filterAppts(String term, int value){
        String input ="SELECT * FROM appointments WHERE (Select extract("+ term +" from Start) = ?)";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
        PreparedStatement ps =DBQuery.getPreparedStatement();
        try {
            ps.setInt(1, value);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        ApptTbl.setItems(dao_appointment.getAppointments(ps));
        updateTable(); 
    }
    
    /**
     * Retrieves all appointments from the database and updates the table to display them.
     */
    private void viewAll(){
        String input ="SELECT * FROM appointments";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
        PreparedStatement ps =DBQuery.getPreparedStatement();
        ApptTbl.setItems(dao_appointment.getAppointments(ps));
        updateTable(); 
    }
    
    /**
     * Initializes the controller class.
     * 
     * <p>LAMBDA #1 chooseMonth eliminates the need to call the same 2 lines of code 4 
     * different places in this class.</p>  
     * <p>LAMBDA #2 chooseWeek eliminates the need to call the same 4 lines of code 3 
     * different places in this class.</p>
     * <p>LAMBDA #3 display eliminates the need to call the same 7 lines of code 2
     * different places in this class.</p>
     * <p>These lambdas also allow changes to be made to a single block of code rather 
     * than changing each place the block appears every time.</p>
     * 
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set the calendar to the current date
        CalendarPkr.setValue(LocalDate.now());
        
        //initialize lambda expressions
        chooseMonth = () -> {int selMonth = CalendarPkr.getValue().getMonthValue();
                                        filterAppts("month", selMonth);};
        chooseWeek = () -> {LocalDate selDate = CalendarPkr.getValue();
                            Locale locale = Locale.getDefault();
                            int weekOfYear = selDate.get(WeekFields.of(locale).weekOfWeekBasedYear())-1;
                            filterAppts("week", weekOfYear);};
        display = () -> {if(AllRBtn.isSelected())
                            viewAll();
                        else if (MonthRBtn.isSelected())
                            chooseMonth.filter();
                        else
                            chooseWeek.filter();
                        updateTable();};
        //display the data
        viewAll();
    }
    
}
