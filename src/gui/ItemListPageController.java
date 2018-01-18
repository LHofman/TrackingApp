package gui;

import domain.DomainController;
import domain.MyDate;
import domain.enums.Status;
import domain.item.Item;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller class for the Home Page
 */
public class ItemListPageController extends GridPane{

    //<editor-fold defaultstate="collapsed" desc="variables">
    
    private final DomainController controller;
    private final SortedList<Item> items;
    private Stage filterStage;
    private FilterWindowController filterWindow;
    
    @FXML
    private Button btnFilter;
    @FXML
    private Button btnNew;
    @FXML
    private TableView<Item> tblItems;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnDelete;
    @FXML
    private TableColumn<Item, String> tbcTitle;
    @FXML
    private TableColumn<Item, MyDate> tbcReleaseDate;
    @FXML
    private TableColumn<Item, CheckBox> tbcInCollection;
    @FXML
    private TableColumn<Item, Status> tbcStatus;
    @FXML
    private Button btnClearFilters;
    
    //</editor-fold>
    
    public ItemListPageController(SortedList<Item> items){
        controller = DomainController.getInstance();
        this.items = items;
        
        GUIHelper.loadFXML("ItemListPage", this);
        
        initTable();
        
    }

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    /**
     * Creates the tableview
     */
    private void initTable(){
        tbcTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        tbcReleaseDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReleaseDate()));
        tbcReleaseDate.setStyle("-fx-alignment: CENTER-RIGHT;");
        tbcInCollection.setCellValueFactory((TableColumn.CellDataFeatures<Item, CheckBox> param) -> {
            Item i = param.getValue();
            
            CheckBox inCollection = new CheckBox();
            inCollection.selectedProperty().setValue(i.isInCollection());
            inCollection.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                i.setInCollection(newValue);
                controller.editEntity(i);
            });
            
            return new SimpleObjectProperty<>(inCollection);
        });
        tbcInCollection.setStyle( "-fx-alignment: CENTER;");
        tbcStatus.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getStatus()));
        
        tblItems.setItems(items);
        items.comparatorProperty().bind(tblItems.comparatorProperty());
        
        tblItems.setOnMouseClicked(click -> {if (click.getClickCount()==2) btnDetails.fire();});
    }
    
    //<editor-fold defaultstate="collapsed" desc="button methods">
    
    @FXML
    private void showDetails(ActionEvent event) {
        Item item = getSelectedItem();
        if (item==null)return;
        if (filterStage!=null) filterStage.hide();
        controller.addToScenePath(new ItemDetailsPageController(item));
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        Item item = getSelectedItem();
        if (item==null) return;
        if (GUIHelper.createAlert(Alert.AlertType.CONFIRMATION, "Confirm removal", "Are you sure you want to remove ".concat(item.toString())))
            controller.deleteEntity(item);
    }

    @FXML
    private void filterResults(ActionEvent event) {
        if (filterStage==null){
            filterStage = new Stage(StageStyle.DECORATED);
            filterStage.setTitle("Filter Items");
            filterStage.setMinWidth(625);
            filterStage.setMinHeight(650);
            filterStage.setResizable(false);
            filterWindow = new FilterWindowController(items);
            Scene scene = new Scene(filterWindow);
            filterStage.setScene(scene);
        }
        filterStage.show();
    }

    @FXML
    private void createNew(ActionEvent event) {
        if (filterStage!=null) filterStage.hide();
        controller.addToScenePath(new ItemDetailsPageController());
    }
    
    @FXML
    private void clearFilters(ActionEvent event) {
        filterWindow.clearFilters();
    }

    //</editor-fold>
    
    /**
     * @return the Item selected in the TableView
     */
    private Item getSelectedItem(){
        return tblItems.getSelectionModel().getSelectedItem();
    }

    //</editor-fold>

}
