
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utils.DBConnection;
import utils.DBQuery;

/**
 * DAO class for interfacing with data in the contacts table of database.
 * Limited to read access only.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class dao_contact {
    
    /**
     * Returns an ObservableList of all Contacts in the database as Contact objects.
     * 
     * @return an ObservableList of all Contacts
     */
    public static ObservableList<Contact> getAllContacts()
    {
        String input = "SELECT * FROM contacts ORDER BY Contact_ID";
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int ContactId = rs.getInt("Contact_ID");
                String ContactName = rs.getString("Contact_Name");
                String Email = rs.getString("Email");
                
                Contact c = new Contact(ContactId, ContactName, Email);
                contacts.add(c);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return contacts;
    }
    
    /**
     * Searches the database and returns the contact as a Contact object
     * @param contID the contact id to search for
     * @return the contact from the database that matches the id, as a Contact object
     */
    public static Contact searchContact(int contID){
        Contact contact = null;    
        try{
            String input = "SELECT * FROM contacts where Contact_ID = ?";
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, contID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int ContactId = rs.getInt("Contact_ID");
                String ContactName = rs.getString("Contact_Name");
                String Email = rs.getString("Email");
                
                contact = new Contact(ContactId, ContactName, Email);   
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return contact;
    }
}
