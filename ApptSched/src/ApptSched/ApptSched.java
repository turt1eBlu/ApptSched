
package ApptSched;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;
import java.sql.Connection;
import utils.CurrUser;
import utils.logFileWriter;


/**
 * The C195Project_jpillo2 program implements GUI-based desktop scheduling application.
 * The program interfaces with an outside MySQL database that stores all data.
 * 
 * JavaDoc folder is included in the project root folder.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ApptSched extends Application{

    /**
     * This is the main method that launches the inventory program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
        //Locale.setDefault(Locale.CANADA_FRENCH);
        Connection conn = DBConnection.startConnection();
        CurrUser currUser = new CurrUser("");
        launch(args);
        if(CurrUser.isLoggedIn()){
            //write log off data to login activity file
            logFileWriter.writeLogoff(CurrUser.getName());
        }
        DBConnection.closeConnection();
    }
    
    /**The main entry method for the JavaFX application.
    * 
    * @param stage the primary stage on which to set the application scene
    */
    @Override
    public void start(Stage stage) {

        ResourceBundle rb = ResourceBundle.getBundle("utils/lang", Locale.getDefault());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScreen.fxml"));
            loader.setResources(rb);
            Parent main = loader.load();
            Scene scene = new Scene(main);
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
