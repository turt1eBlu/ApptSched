
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimeHandler;

/**
 * DAO class for interfacing with data in the countries table of database.
 * Limited to read access only.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class dao_country {
    /**
     * Returns an ObservableList of all Countries in the database as Country objects.
     * 
     * @return an ObservableList of all Countries
     */
    public static ObservableList<Country> getAllCountries()
    {
        String input = "SELECT * FROM countries ORDER BY Country";
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try{
            DBQuery.setPreparedStatment(DBConnection.getConnection(), input);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String Country = rs.getString("Country");
                String CreatedBy = rs.getString("Created_By");
                String UpdatedBy = rs.getString("Last_Updated_By");
                ZonedDateTime CreateDate = TimeHandler.dt2zdt(rs.getTimestamp("Create_Date"));
                ZonedDateTime LastUpdate = TimeHandler.dt2zdt(rs.getTimestamp("Last_Update")); 
                int Country_ID = rs.getInt("Country_ID");

                
                Country c = new Country(Country_ID, Country, CreatedBy, UpdatedBy, 
                        CreateDate, LastUpdate);
                countries.add(c);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return countries;
    }
    
    /**
     * Searches the database and returns the country as a Country object
     * @param name the country name to search for
     * @return the country from the database that matches the name, as a Country object
     */
    public static Country searchCountry(String name) {
        Country country = null;
        try {
            String search = "SELECT * from countries where Country = ?";
            DBQuery.setPreparedStatment(DBConnection.getConnection(), search);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                String Country = rs.getString("Country");
                String CreatedBy = rs.getString("Created_By");
                String UpdatedBy = rs.getString("Last_Updated_By");
                ZonedDateTime CreateDate = TimeHandler.dt2zdt(rs.getTimestamp("Create_Date"));
                ZonedDateTime LastUpdate = TimeHandler.dt2zdt(rs.getTimestamp("Last_Update"));
                int Country_ID = rs.getInt("Country_ID");

                country = new Country(Country_ID, Country, CreatedBy, UpdatedBy,
                        CreateDate, LastUpdate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return country;
         
    }
}
