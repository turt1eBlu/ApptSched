
package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Utility class for creating prepared statements to query the database.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class DBQuery {
    private static PreparedStatement preparedStatement;
     
    /**
     * Creates a prepared statement for querying the database.
     * @param conn database connection
     * @param sqlStatement SQL query statement
     */
    public static void setPreparedStatment(Connection conn, String sqlStatement){
        try {
            preparedStatement = conn.prepareStatement(sqlStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Returns the prepared statement.
     * @return prepared statement
     */
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }
    
}
