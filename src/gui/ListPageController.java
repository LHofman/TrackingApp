package gui;

import domain.DomainController;
import domain.item.Item;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class ListPageController<E extends Item, T extends Object> extends FlowPane {

    //<editor-fold defaultstate="collapsed" desc="variables">
    protected final DomainController controller;
    protected final E entity;
    protected ObservableList<T> list;
    protected SortedList<T> sortedList;
    protected CustomGridPane grid;

    @FXML
    protected Label lblTitle;
    @FXML
    private GridPane gridNewObject;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;
    @FXML
    protected TableView<T> tblObjects;
    @FXML
    private VBox vboxIndexButtons;
    @FXML
    private Button btnFirst;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnLast;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    public ListPageController(E entity, boolean hasIndex) {
        controller = DomainController.getInstance();
        this.entity = entity;

        GUIHelper.loadFXML("ListPageController", this);

        vboxIndexButtons.setVisible(hasIndex);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    protected void init() {

        setUpPane();
        setUpTable();

        grid = new CustomGridPane(gridNewObject);
        setUpGrid();

    }

    /**
     * Sets title and items
     */
    private void setUpPane() {
        lblTitle.setText(getTitle());
        this.list = FXCollections.observableList(getList());
        this.sortedList = new SortedList<>(this.list);
    }

    /**
     * Creates Tablecolumns, Sets and binds the Items
     */
    private void setUpTable() {
        tblObjects.getColumns().addAll(getTableColumns());

        tblObjects.setItems(sortedList);
        sortedList.comparatorProperty().bind(tblObjects.comparatorProperty());
        tblObjects.setOnMouseClicked(event -> {if (event.getClickCount()==2) btnDetails.fire();});
    }

    /**
     * Creates the grid to add a GameObjective
     */
    private void setUpGrid() {
        setGridControls();

        grid.addButtons(saveEvent -> {
            try {
                save();
            } catch (Exception e) {
                controller.throwException(e);
            }
        }, cancelEvent -> {
            grid.clearFields();
        });

        grid.setUpConstraints(400, 200);

    }

    /**
     * Gets the selected GameObjective from the TableView
     *
     * @return
     */
    protected T getSelected() {
        return tblObjects.getSelectionModel().getSelectedItem();
    }

    //<editor-fold defaultstate="collapsed" desc="button methods">
    @FXML
    private void showDetails(ActionEvent event) {
        T object = getSelected();
        if (object == null) {
            return;
        }
        showDetails(object);
    }

    @FXML
    private void delete(ActionEvent event) {
        T object = getSelected();
        if (object == null) return;
        if (GUIHelper.createAlert(Alert.AlertType.CONFIRMATION, "Confirm removal", String.format("Are you sure you want to remove \"%s\"", getTitleSelected(object)))) {
            deleteObject(object);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        controller.removeSceneFromPath();
    }

    @FXML
    protected void moveFirst(ActionEvent event) {}
    @FXML
    protected void moveUp(ActionEvent event) {}
    @FXML
    protected void moveDown(ActionEvent event) {}
    @FXML
    protected void moveLast(ActionEvent event) {}

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="abstract methods">
    /**
     * @return The title for the pane
     */
    protected abstract String getTitle();

    /**
     * @return The list for the tableview
     */
    protected abstract List<T> getList();

    /**
     * @return The columns for the TableView
     */
    protected abstract TableColumn<T, Object>[] getTableColumns();

    /**
     * Creates the controls of the grid
     */
    protected abstract void setGridControls();

    /**
     * Shows details of the object
     *
     * @param object
     */
    protected abstract void showDetails(T object);

    /**
     * Saves the entity
     */
    protected abstract void save();

    /**
     * @param object 
     * @return The title from the object
     */
    protected String getTitleSelected(T object){
        return object.toString();
    }
    
    /**
     * Deletes the object
     *
     * @param object
     */
    protected abstract void deleteObject(T object);

    //</editor-fold>
    
    //</editor-fold>
    
}
