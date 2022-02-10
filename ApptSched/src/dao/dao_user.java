
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ViewableUser;
import utils.DBConnection;
import utils.DBQuery;

/**
 * DAO class for interfacing with data in the users table of database.
 * Limited to read access only.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class dao_user {
    
    /**
     * Returns an ObservableList of all Users in the database as ViewableUser objects.
     * ViewableUsers only contain user names and ids.
     * @return an ObservableList of all Users
     */
    public static ObservableList<ViewableUser> getAllUserNames(){
        ObservableList<ViewableUser> allUsers = FXCollections.observableArrayList();
        String input = "SELECT * FROM users ORDER BY User_ID";
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String UserName = rs.getString("User_Name"); 
                int UserId = rs.getInt("User_ID");              
                ViewableUser u = new ViewableUser(UserId, UserName);
                allUsers.add(u);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allUsers;
    }
    
    /**
     * Searches database for matching row with username and password
     * @param user the user name to search for
     * @param pswd the password to search for
     * @return success of finding a matching row
     */
    public static boolean validateUser(String user, String pswd){
        boolean matchFound = false;
        try{
            String sql = "SELECT User_Name, Password FROM users WHERE users.User_Name = ? AND users.Password = ?";
            DBQuery.setPreparedStatment(DBConnection.getConnection(), sql);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, user);
            ps.setString(2, pswd);
            ResultSet rs = ps.executeQuery();
            matchFound = rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return matchFound;  
    }
    
    /**
     * Searches the database and returns the user as a ViewableUser object
     * @param uid the user id to search for
     * @return the user from the database that matches the user id, as a ViewableUser object
     */
    public static ViewableUser getViewUser(int uid){
        ViewableUser vu = null;
        String input = "SELECT User_Name FROM users WHERE User_ID = ?";
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String UserName = rs.getString("User_Name");               
                vu = new ViewableUser(uid, UserName);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return vu;
    }
    
    /**
     * Searches the users database by user name and returns the user id.
     * 
     * @param name a string containing the user name to search
     * @return the user id that matches the user name
     */
    public static int getUserID(String name){
        int userid = -1;
        String input = "SELECT User_ID FROM users WHERE User_Name = ?";
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                userid = rs.getInt("User_ID");               
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return userid;
    }
}
