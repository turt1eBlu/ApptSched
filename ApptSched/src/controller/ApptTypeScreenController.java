
package controller;

import dao.dao_appointment;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.TypeCount;
import utils.GUI;

/**
 * FXML Controller class for the ApptTypeScreen FXML page.
 *
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ApptTypeScreenController implements Initializable {
    
    GUI gui = new GUI();
    
    @FXML
    private DatePicker DatePkr;
    
    @FXML
    private TableView<TypeCount> ApptCountTbl;

    @FXML
    private TableColumn<TypeCount, String> ApptTypeCol;

    @FXML
    private TableColumn<TypeCount, Integer> ApptCountCol;

    /**
     * Returns user to the main form.
     * 
     * @param event OnButtonClick event
     */
    @FXML
    void OnActionReturnToMain(ActionEvent event) {
        gui.loadScreen("/view/SchedMgrMain.fxml", event);
    }
    
    /**
     * Calls the updateTable method when a calendar date is chosen.
     * 
     * @param event choosing a date on the calendar picker control
     */
    @FXML
    void OnActionUpdateTable(ActionEvent event) {
        updateTable();
    }
    
    /**
     * Updates the table view with data for the chosen month.
     */
    private void updateTable(){
        int selMonth = DatePkr.getValue().getMonthValue();
        ApptCountTbl.setItems(dao_appointment.getTypeCount(selMonth));
        ApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ApptCountCol.setCellValueFactory(new PropertyValueFactory<>("Count"));
    }
    
    /**
     * Initializes the controller class.
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatePkr.setValue(LocalDate.now());
        updateTable();
    }    
    
}
