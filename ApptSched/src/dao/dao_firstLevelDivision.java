
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimeHandler;

/**
 * DAO class for interfacing with data in the first_level_divisions table of database.
 * Limited to read access only.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class dao_firstLevelDivision {
    
    /**
     * Returns an ObservableList of all first level divisions in the database as 
     * FirstLevelDivision objects.
     * 
     * @param country the country id number
     * @return an ObservableList of all First Level Divisions
     */
    public static ObservableList<FirstLevelDivision> getDivisions(int country)
    {
        String input = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " 
                + String.valueOf(country) + " ORDER BY Division";
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int Division_ID = rs.getInt("Division_ID");
                String Division = rs.getString("Division");
                String CreatedBy = rs.getString("Created_By");
                String UpdatedBy = rs.getString("Last_Updated_By");
                ZonedDateTime CreateDate = TimeHandler.dt2zdt(rs.getTimestamp("Create_Date"));
                ZonedDateTime LastUpdate = TimeHandler.dt2zdt(rs.getTimestamp("Last_Update")); 
                int Country_ID = rs.getInt("Country_ID");

                
                FirstLevelDivision fld = new FirstLevelDivision(Division_ID, Division,
                        CreatedBy, UpdatedBy, CreateDate, LastUpdate, Country_ID);
                divisions.add(fld);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return divisions;
    }
    
    /**
     * Searches the database and returns the first level division as a 
     * FirstLevelDivision object
     * @param name the division name to search for
     * @return the division from the database that matches the name, as a FirstLevelDivision object
     */
    public static FirstLevelDivision searchDivision(String name) {
        FirstLevelDivision fld = null;
        try {
            String search = "SELECT * from first_level_divisions where Division = ?";
            DBQuery.setPreparedStatment(DBConnection.getConnection(), search);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                int Division_ID = rs.getInt("Division_ID");
                String Division = rs.getString("Division");
                String CreatedBy = rs.getString("Created_By");
                String UpdatedBy = rs.getString("Last_Updated_By");
                ZonedDateTime CreateDate = TimeHandler.dt2zdt(rs.getTimestamp("Create_Date"));
                ZonedDateTime LastUpdate = TimeHandler.dt2zdt(rs.getTimestamp("Last_Update"));
                int Country_ID = rs.getInt("Country_ID");

                fld = new FirstLevelDivision(Division_ID, Division,
                        CreatedBy, UpdatedBy, CreateDate, LastUpdate, Country_ID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fld;
    } 
}
