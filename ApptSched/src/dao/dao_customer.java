
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.ViewableCustomer;
import utils.DBConnection;
import utils.DBQuery;
import utils.MessageDisplay;
import utils.TimeHandler;

/**
 * DAO class for interfacing with data in customers table of database.
 * Allows CRUD operations on table rows.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class dao_customer {
    
    /**
     * Returns an ObservableList of all Customers in the database as 
     * ViewableCustomer objects.
     * 
     * @return an observable list of ViewableCustomer objects
     */
    public static ObservableList<ViewableCustomer> getCustomers()
    {
        String input = "SELECT customers.Customer_ID, customers.Customer_Name, "
                + "customers.Address, first_level_divisions.Division, "
                + "customers.Postal_Code, countries.Country, customers.Phone "
                + "FROM customers inner join "
                + "(first_level_divisions inner join countries on "
                + "first_level_divisions.COUNTRY_ID = countries.Country_ID) "
                + "on first_level_divisions.Division_ID = customers.Division_ID"
                + " ORDER BY customers.Customer_ID;";
        
        ObservableList<ViewableCustomer> allCustomers = FXCollections.observableArrayList();
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int CustomerId = rs.getInt("Customer_ID");
                String CustomerName= rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String firstLevDiv = rs.getString("Division");
                String Country = rs.getString("Country");
                String PostalCode = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                                
                ViewableCustomer Vcust = new ViewableCustomer(CustomerId, CustomerName, Address, 
                        firstLevDiv, Country, PostalCode, Phone);
                allCustomers.add(Vcust);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Modifies a customer in the database.
     * Updates row in database containing the matching customer id value.
     * 
     * @param cust The Customer object to modify in the database
     * @return The success of the update operation
     */
    public static boolean modifyCustomer(Customer cust){
        boolean success = false;
        //update appointment in database
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, "
                + "Phone = ?, Last_Update = ?, Last_Updated_By = ?, "
                + "Division_ID = ? WHERE Customer_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {
            //add values to prepared statement
            ps.setString(1, cust.getCustomerName());
            ps.setString(2, cust.getAddress());
            ps.setString(3, cust.getPostalCode());
            ps.setString(4, cust.getPhone());
            ps.setTimestamp(5, TimeHandler.zdt2ts(cust.getLastUpdate()));
            ps.setString(6, cust.getUpdatedBy());
            ps.setInt(7, cust.getDivision());
            ps.setInt(8, cust.getCustomerId());
            //update the database
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
     * Adds a customer to the database.
     * Database auto-assigns the customer id, create date and modify date
     * values.
     * 
     * @param cust The Customer object to insert into the database
     * @return The success of the update operation
     */
    public static boolean addCustomer(Customer cust){
        boolean success = false;
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, "
                + "Phone, Last_Updated_By, Division_ID, Created_By)"
                 + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {
            //add values to prepared statement
            ps.setString(1, cust.getCustomerName());
            ps.setString(2, cust.getAddress());
            ps.setString(3, cust.getPostalCode());
            ps.setString(4, cust.getPhone());
            ps.setString(5, cust.getUpdatedBy());
            ps.setInt(6, cust.getDivision());
            ps.setString(7, cust.getCreatedBy());
            //update the database
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
     * Deletes a customer from the database.
     * Deletes all appointments with a matching customer id.
     * Deletes row in database containing the matching customer id value.
     * 
     * @param cust The ViewableCustomer object to delete in the database
     * @return The success of the delete operation
     */
    public static boolean deleteCustomer(ViewableCustomer cust){
        boolean success = false;
        //customer can only be deleted if they don't have appointments
        //delete all appointments for customer
        ObservableList<Appointment> custAppts = dao_appointment.getCustAppts(cust.getCustomerId());
        for (Appointment a: custAppts)
            dao_appointment.deleteAppointment(a);
        //delete customer from database
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
        PreparedStatement ps = DBQuery.getPreparedStatement();
         try {   
            ps.setInt(1, cust.getCustomerId());
            ps.executeUpdate();
            if (ps.getUpdateCount()> 0){
                success = true;
                String message = "Customer #" + cust.getCustomerId() + " " + 
                        cust.getCustomerName() + " has been deleted successfully!";
                MessageDisplay.displayMessage(message);
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            String message = "Customer could not be deleted.";
            MessageDisplay.displayWarning(message);
        }
        return success;
    }
    
    /**
     * Searches the customers database by customer id and returns the matching row
     * as a ViewableCustomer object.
     * 
     * @param cust_id the customer id to search for
     * @return the ViewableCustomer object created from the matching row
     */
    public static ViewableCustomer getViewCust(int cust_id){
        ViewableCustomer customer = null;
        String input = "SELECT customers.Customer_ID, customers.Customer_Name, "
                + "customers.Address, first_level_divisions.Division, "
                + "customers.Postal_Code, countries.Country, customers.Phone "
                + "FROM customers inner join "
                + "(first_level_divisions inner join countries on "
                + "first_level_divisions.COUNTRY_ID = countries.Country_ID) "
                + "on first_level_divisions.Division_ID = customers.Division_ID "
                + "WHERE Customer_ID = ?;";
        
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, cust_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int CustomerId = rs.getInt("Customer_ID");
                String CustomerName= rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String firstLevDiv = rs.getString("Division");
                String Country = rs.getString("Country");
                String PostalCode = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                                
                customer = new ViewableCustomer(CustomerId, CustomerName, Address, 
                        firstLevDiv, Country, PostalCode, Phone);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }        
        return customer;
    } 
}
