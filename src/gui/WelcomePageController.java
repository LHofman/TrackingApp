package gui;

import domain.DomainController;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class for the Welcome Page
 */
public class WelcomePageController extends BorderPane implements Observer{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    private final DomainController controller;
    
    @FXML
    private MenuItem miAbout;
    @FXML
    private MenuItem miExit;
    @FXML
    private MenuItem miHome;
    @FXML
    private Label lblError;
    
    //</editor-fold>
    
    public WelcomePageController(){
        this.controller = DomainController.getInstance();
        controller.addObserver(this);
        GUIHelper.loadFXML("WelcomePage", this);
    }

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @FXML
    private void miAboutOnAction(ActionEvent event) {
        GUIHelper.createAlert(Alert.AlertType.INFORMATION, "About", "This project is made to be able to track everything from 1 platform.");
    }

    @FXML
    private void miExitOnAction(ActionEvent event) {System.exit(0);}

    @FXML
    private void miHomeOnAction(ActionEvent event) {
        setNewPath(new ItemListPageController(controller.getAllItems()));
    }
    
    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof Pane){
            lblError.setText("");
            centerProperty().set((Pane)o1);
        }else if(o1 instanceof String){
            String value = o1.toString();
            if (value.startsWith("Exception"))
                lblError.setText(value.substring(10));
        }
    }
    
    /**
     * Shows the specified scene after closing all open scenes
     * Asks confirmation if other scenes are open
     * @param pane 
     */
    private void setNewPath(Pane pane){
        if (!confirmClosingOperation()) return;
        controller.setNewPath(pane);
    }
    /**
     * Asks closing confirmation if multiple scenes are open
     * @return 
     */
    private boolean confirmClosingOperation(){
        return controller.getScenePathLength()<2 || 
            GUIHelper.createAlert(Alert.AlertType.CONFIRMATION, "Confirm closing of operations", 
                    "Are you sure you want to close the current operation?");
    }
    
    //</editor-fold>

}
