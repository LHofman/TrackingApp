package gui;

import domain.DomainController;
import domain.entity.GameObjective;
import domain.item.Game;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameObjectivesPageController extends FlowPane {

    //<editor-fold defaultstate="collapsed" desc="variables">
    private final DomainController controller;
    private final Game game;
    private final GameObjective parent;
    private final ObservableList<GameObjective> list;
    private final SortedList<GameObjective> sortedList;
    private final CustomGridPane grid;

    @FXML
    private Label lblTitle;
    @FXML
    private GridPane gridNewObject;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<GameObjective> tblObjects;
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
    public GameObjectivesPageController(Game game, GameObjective parent) {
        controller = DomainController.getInstance();
        this.game = game;
        this.parent = parent;

        GUIHelper.loadFXML("GameObjectivesPage", this);

        String title;

        List<GameObjective> list;
        if (parent!=null) {
            list = parent.getObjectives();
            title = parent.getObjective();
        }
        else {
            list = game.getObjectives();
            title = game.getTitle();
        }
        lblTitle.setText(title.concat(": objectives"));
        this.list = FXCollections.observableList(list);
        this.sortedList = new SortedList<>(this.list);
        setUpTable();
        sortByIndex();

        grid = new CustomGridPane(gridNewObject);
        setUpGrid();
    }
    public GameObjectivesPageController(Game game) {
        this(game, null);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Creates Tablecolumns,
     * Sets and binds the Items
     */
    private void setUpTable() {
        TableColumn<GameObjective, Integer> index = new TableColumn<>("Index");
        index.setCellValueFactory(cellData -> new SimpleObjectProperty(((GameObjective) cellData.getValue()).getIndex()));
        TableColumn<GameObjective, String> objective = new TableColumn<>("Objective");
        objective.setCellValueFactory(cellData -> new SimpleStringProperty(((GameObjective) cellData.getValue()).getObjective()));
        TableColumn<GameObjective, CheckBox> completed = new TableColumn<>("Completed");
        completed.setCellValueFactory((TableColumn.CellDataFeatures<GameObjective, CheckBox> param) -> {
            GameObjective obj = (GameObjective) param.getValue();

            CheckBox chbCompleted = new CheckBox();
            chbCompleted.selectedProperty().setValue(obj.isCompleted());
            chbCompleted.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                obj.setCompleted(newValue);
                controller.editEntity(game);
            });

            return new SimpleObjectProperty<>(chbCompleted);
        });
        TableColumn<GameObjective, CheckBox> hasSubObjectives = new TableColumn<>("Sub-Objectives");
        hasSubObjectives.setCellValueFactory((TableColumn.CellDataFeatures<GameObjective, CheckBox> param) -> {
            GameObjective obj = (GameObjective)param.getValue();
            CheckBox chbHasSubObjectives = new CheckBox();
            chbHasSubObjectives.setDisable(true);
            chbHasSubObjectives.selectedProperty().setValue(obj.hasObjectives());
            return new SimpleObjectProperty<>(chbHasSubObjectives);
        });
        tblObjects.getColumns().addAll(index, objective, completed, hasSubObjectives);
        
        tblObjects.setItems(sortedList);
        sortedList.comparatorProperty().bind(tblObjects.comparatorProperty());
    }

    /**
     * Creates the grid to add a GameObjective
     */
    private void setUpGrid() {
        grid.addNode("lblObjective", "Label", "Objective: ");
        grid.addNode("txtObjective", "TextField");
        grid.addRow("lblObjective", "txtObjective");

        grid.addNode("lblCompleted", "Label", "Completed: ");
        grid.addNode("chbCompleted", "CheckBox");
        grid.addRow("lblCompleted", "chbCompleted");

        grid.addButtons(saveEvent -> {
            try {
                String name = ((TextField) grid.getNode("txtObjective")).getText();
                boolean completed = ((CheckBox) grid.getNode("chbCompleted")).isSelected();

                GameObjective objective = new GameObjective(list.size() + 1, name, completed);
                if (parent!=null) {
                    parent.addObjectives(objective);
                    controller.editEntity(parent);
                }else{
                    game.addObjectives(objective);
                    controller.editEntity(game);
                }
                
                grid.clearFields();
            } catch (Exception e) {
                controller.throwException(e);
            }

        }, cancelEvent -> {
            grid.clearFields();
        });

        grid.setUpConstraints(400, 200);

    }

    //<editor-fold defaultstate="collapsed" desc="button methods">
    @FXML
    private void showDetails(ActionEvent event) {
        GameObjective gameObjective = getSelected();
        if (gameObjective == null) return;
        controller.addToScenePath(new EntityDetailsPageController(gameObjective));
    }

    @FXML
    private void delete(ActionEvent event) {
        GameObjective gameObjective = getSelected();
        if (gameObjective == null) return;
        if (GUIHelper.createAlert(Alert.AlertType.CONFIRMATION, "Confirm removal", "Are you sure you want to remove \"".concat(gameObjective.toString()).concat("\""))) {
            if (parent!=null){
                parent.removeObjectives(gameObjective);
                controller.editEntity(parent);
            }else{
                game.removeObjectives(gameObjective);
                controller.editEntity(game);
            }
            controller.deleteEntity(gameObjective);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        controller.removeSceneFromPath();
    }

    @FXML
    private void moveFirst(ActionEvent event) {
        changeIndex(true, true);
    }

    @FXML
    private void moveUp(ActionEvent event) {
        changeIndex(true, false);
    }

    @FXML
    private void moveDown(ActionEvent event) {
        changeIndex(false, false);
    }

    @FXML
    private void moveLast(ActionEvent event) {
        changeIndex(false, true);
    }

    //</editor-fold>
    
    /**
     * Gets the selected GameObjective from the TableView
     * @return 
     */
    private GameObjective getSelected() {
        return tblObjects.getSelectionModel().getSelectedItem();
    }

    /**
     * Changes the Index of the Selected GameObjective
     * @param up direction of the index
     * @param allTheWay to top/bottom of the list
     */
    private void changeIndex(boolean up, boolean allTheWay) {
        GameObjective gameObjective = getSelected();
        if (gameObjective == null) return;
        int index = gameObjective.getIndex();
        while ((up && index > 1) || (!up && index < list.size())) {
            changeIndex(gameObjective, index, up);
            if (!allTheWay) break;
            index = gameObjective.getIndex();
        }
        controller.editEntity(game);
        sortByIndex();
    }

    /**
     * Changes the index of the passed GameObjective by 1
     * @param gameObjective the objective to be changed
     * @param index the index of the gameObjective
     * @param up the direction of the index
     */
    private void changeIndex(GameObjective gameObjective, int index, boolean up) {
        int newIndex = up ? index - 1 : index + 1;
        (list.get(newIndex - 1)).setIndex(index);
        gameObjective.setIndex(newIndex);
    }

    /**
     * Sorts the GameObjectives of the TableView by Index
     */
    private void sortByIndex() {
        this.list.sort((GameObjective t, GameObjective t1) -> t.getIndex() - t1.getIndex());
    }

    //</editor-fold>
}
