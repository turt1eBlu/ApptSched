
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for creating and accessing a connection to the database.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class DBConnection {
    //JDBC URL parts
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR_NAME = ":mysql:";
    private static final String IP_ADDRESS = "";
    private static final String DB_NAME = "";
    private static final String TIMEZONE = "";
    
    //JBDC URL
    private static final String JDBC_URL = PROTOCOL + VENDOR_NAME + IP_ADDRESS + DB_NAME + TIMEZONE;
    
    //driver and connection interface reference
    private static final String MYSQL_JDBC_DRIVER = "";
    private static Connection conn = null;
   
    //user name and password
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";
    
    /**
     * Creates a connection with the database.
     * @return database connection
     */
    public static Connection startConnection()
    {
        try{
            Class.forName(MYSQL_JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
            System.out.println("Connection Opened Successfully");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
       return conn; 
    }
   
    /**
     * Returns the database connection.
     * @return database connection
     */
    public static Connection getConnection()
    {
        return conn;
    }
    
    /**
     * Closes the connection with the database.
     */
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection Closed Successfully");
        }
        catch(SQLException e){
            //Program closing--do nothing
        }
    }
    
}
