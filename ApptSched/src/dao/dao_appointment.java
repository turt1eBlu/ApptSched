
package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import model.TypeCount;
import utils.DBQuery;
import utils.MessageDisplay;
import utils.TimeHandler;

/**
 * DAO class for interfacing with data in appointment table of database.
 * Allows CRUD operations on table rows.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class dao_appointment {
    
    /**
     * Returns an ObservableList of Appointments in the database matching the 
     * selection criteria as Appointment objects.
     * 
     * @param input the prepared statement containing a database select statement
     * @return an observable list of Appointment objects
     */
    public static ObservableList<Appointment> getAppointments(PreparedStatement input)
    {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try{
            ResultSet rs = input.executeQuery();
            while(rs.next()){
                int AppointmentId = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                ZonedDateTime Start = TimeHandler.dt2zdt(rs.getTimestamp("Start"));
                ZonedDateTime End = TimeHandler.dt2zdt(rs.getTimestamp("End")); 
                String CreatedBy = rs.getString("Created_By");
                String UpdatedBy = rs.getString("Last_Updated_By");
                ZonedDateTime CreateDate = TimeHandler.dt2zdt(rs.getTimestamp("Create_Date"));
                ZonedDateTime LastUpdate = TimeHandler.dt2zdt(rs.getTimestamp("Last_Update")); 
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");
                
                Appointment app = new Appointment(AppointmentId, Title, Description, 
                        Location, Type, Start, End, CreatedBy, UpdatedBy, CreateDate, 
                        LastUpdate, CustomerID, UserID, ContactID);
                allAppointments.add(app);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allAppointments;
    }
    
    /**
     * Modifies an appointment in the database.
     * Updates row in database containing the matching appointment id value.
     * 
     * @param appt The Appointment object to modify in the database
     * @return The success of the update operation
     */
    public static boolean modifyAppointment(Appointment appt){
        boolean success = false;
        //update appointment in database
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Type = ?, "
                + "Location = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, "
                + "Customer_ID = ?, User_ID = ?, Contact_ID =? "
                + "WHERE appointments.Appointment_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {   
            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getType());
            ps.setString(4, appt.getLocation());
            ps.setTimestamp(5, TimeHandler.zdt2ts(appt.getStart()));
            ps.setTimestamp(6,  TimeHandler.zdt2ts(appt.getEnd()));
            ps.setTimestamp(7, TimeHandler.zdt2ts(appt.getLastUpdate()));
            ps.setString(8, appt.getUpdatedBy());
            ps.setInt(9, appt.getCustomerID());
            ps.setInt(10, appt.getUserID());
            ps.setInt(11, appt.getContactID());
            ps.setInt(12, appt.getAppointmentId());
            
            ps.executeUpdate();
            if (ps.getUpdateCount() > 0)
                success = true;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }
    
    /**
     * Adds an appointment to the database.
     * Database auto-assigns the appointment id, create date and modify date
     * values.
     * 
     * @param appt The Appointment object to insert into the database
     * @return The success of the update operation
     */
    public static boolean addAppointment(Appointment appt){
        boolean success = false;
        String insertStmt = "INSERT INTO appointments (Title, Description, Type, "
                + "Location, Start, End, Created_By, Last_Updated_By, "
                + "Customer_ID, User_ID, Contact_ID) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), insertStmt);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {   
            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDescription());
            ps.setString(3, appt.getType());
            ps.setString(4, appt.getLocation());
            ps.setTimestamp(5, TimeHandler.zdt2ts(appt.getStart()));
            ps.setTimestamp(6,  TimeHandler.zdt2ts(appt.getEnd()));
            ps.setString(7, appt.getCreatedBy());
            ps.setString(8, appt.getUpdatedBy());
            ps.setInt(9, appt.getCustomerID());
            ps.setInt(10, appt.getUserID());
            ps.setInt(11, appt.getContactID());
            
            ps.executeUpdate();
            if (ps.getUpdateCount() > 0)
                success = true;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }
    
    /**
     * Deletes an appointment from the database.
     * Deletes row in database containing the matching appointment id value.
     * 
     * @param selAppt The Appointment object to delete in the database
     * @return The success of the delete operation
     */
    public static boolean deleteAppointment(Appointment selAppt){
        boolean success = false;
        String sql = "DELETE FROM appointments WHERE appointments.Appointment_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {   
            ps.setInt(1, selAppt.getAppointmentId());
            ps.executeUpdate();
            if (ps.getUpdateCount()> 0){
                success = true;
               String message = "Appointment #" + selAppt.getAppointmentId() +
                        " Type: " + selAppt.getType() + " has been cancelled.";
                MessageDisplay.displayMessage(message); 
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            String message = "Appointment could not be cancelled.";
            MessageDisplay.displayWarning(message);
        }
        return success;
    }
    
    /**
     * Returns an ObservableList of Appointment objects matching the appointment id value.
     * 
     * @param appt_id appointment id value to search for
     * @return an observable list containing Appointments
     */
    public static ObservableList<Appointment> lookupAppointment(int appt_id){    
        String sql = "SELECT * FROM appointments WHERE appointments.Appointment_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {   
            ps.setInt(1, appt_id);    
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        ObservableList<Appointment> appt = getAppointments(ps);
        return appt;
    }
    
    /**
     * Returns an ObservableList of Appointment objects matching the customer id value.
     * 
     * @param custID customer id value to search for
     * @return an observable list containing all Appointments for the customer
     */
    public static ObservableList<Appointment> getCustAppts(int custID){    
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {   
            ps.setInt(1, custID);    
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        ObservableList<Appointment> appt = getAppointments(ps);
        return appt;
    }
    
    /**
     * Checks an appointment time against the database to determine if there
     * is an existing appointment for the customer that has a time conflict.
     * Only checks time against appointments with non-matching Appointment IDs.
     * 
     * @param newStart Start of appointment to check
     * @param newEnd End of appointment to check
     * @param CustID Customer ID for appointment
     * @param apptID Appointment ID for new appointment
     * @return success of finding an overlapping appointment
     */
    public static boolean checkOverlap(ZonedDateTime newStart, ZonedDateTime newEnd, int CustID, int apptID){
        String sql = "SELECT Start, End, Appointment_ID FROM appointments WHERE Customer_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        boolean overlap = false;
         try {   
            ps.setInt(1, CustID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int appt_id = rs.getInt("Appointment_ID");
                ZonedDateTime oldStart = TimeHandler.dt2zdt(rs.getTimestamp("Start"));
                ZonedDateTime oldEnd = TimeHandler.dt2zdt(rs.getTimestamp("End"));
                if (appt_id != apptID){
                    //overlap = overlapCheck(newStart, newEnd, oldStart, oldEnd);
                    //new appointment before existing appointment
                     boolean case1 = newEnd.isBefore(oldStart) || newEnd.isEqual(oldStart);
        
                    //new appointment after existing appointment
                    boolean case2 = newStart.isAfter(oldEnd) || newStart.isEqual(oldEnd);
                    overlap = !( case1 || case2 );
                }
                if (overlap == true){
                    break; }
            }   
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return overlap;
    }
    
    /**
     * Determines if there are appointments starting in the next 15 minutes for 
     * the selected user.
     * 
     * @param user the string containing the name of the user to check 
     */
    public static void getAlert(String user){
        LocalTime loginTime = LocalTime.now();
        LocalDate loginDate = LocalDate.now();
        String sql = "SELECT Appointment_ID, Start FROM appointments WHERE User_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        long timeDiff;
        int numMessages = 0;
        try {   
            ps.setInt(1,dao_user.getUserID(user));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                int apptID = rs.getInt("Appointment_ID");
                ZonedDateTime Start = TimeHandler.dt2zdt(rs.getTimestamp("Start"));
                LocalTime startTime = Start.toLocalTime();
                LocalDate startDate = Start.toLocalDate();
                if (loginDate.isEqual(startDate)){
                    timeDiff = ChronoUnit.MINUTES.between(loginTime, startTime);
                    if(timeDiff>=0 && timeDiff<15){
                        numMessages++;
                        MessageDisplay.displayMessage("Appt# " + apptID + 
                                " starts at " + startTime + " today " + startDate);
                    }   
                }
            } 
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        if(numMessages==0) 
            MessageDisplay.displayMessage("You have no appointments "
                               + "starting in the next 15 minutes.");
    }
    
    /**
     * Returns the total number of customer appointments by type and month.
     * 
     * @param month the month to get type totals for
     * @return an ObservableList of TypeCount objects
     */
    public static ObservableList<TypeCount> getTypeCount(int month)
    {
        String input = "SELECT Type, COUNT(Type) as Count FROM appointments WHERE "
                + "(SELECT EXTRACT(month FROM Start) = ?) GROUP BY Type";
        ObservableList<TypeCount> typecounts = FXCollections.observableArrayList();
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int count = rs.getInt("Count");
                String type = rs.getString("Type");
                
                TypeCount tc = new TypeCount(type, count);
                typecounts.add(tc);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return typecounts;
    }

}
