package gui;

import domain.DomainHelper;
import domain.entity.GameObjective;
import domain.item.Game;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class GameObjectivesPageController extends ListPageController<Game, GameObjective> {

    //<editor-fold defaultstate="collapsed" desc="variables">
    private final GameObjective parent;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public GameObjectivesPageController(Game game, GameObjective parent) {
        super(game, true);
        this.parent = parent;
        init();
    }

    public GameObjectivesPageController(Game game) {
        this(game, null);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="button methods">
    
    @Override
    protected void moveFirst(ActionEvent event) {
        changeIndex(true, true);
    }

    @Override
    protected void moveUp(ActionEvent event) {
        changeIndex(true, false);
    }

    @Override
    protected void moveDown(ActionEvent event) {
        changeIndex(false, false);
    }

    @Override
    protected void moveLast(ActionEvent event) {
        changeIndex(false, true);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="abstract methods">
    @Override
    protected String getTitle() {
        return (parent != null ? parent.getObjective() : entity.getTitle()).concat(": objectives");
    }

    @Override
    protected List<GameObjective> getList() {
        return parent != null ? parent.getObjectives() : entity.getObjectives();
    }

    @Override
    protected TableColumn<GameObjective, Object>[] getTableColumns() {
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
                controller.editEntity(entity);
            });

            return new SimpleObjectProperty<>(chbCompleted);
        });
        TableColumn<GameObjective, CheckBox> hasSubObjectives = new TableColumn<>("Sub-Objectives");
        hasSubObjectives.setCellValueFactory((TableColumn.CellDataFeatures<GameObjective, CheckBox> param) -> {
            GameObjective obj = (GameObjective) param.getValue();
            CheckBox chbHasSubObjectives = new CheckBox();
            chbHasSubObjectives.setDisable(true);
            chbHasSubObjectives.selectedProperty().setValue(obj.hasObjectives());
            return new SimpleObjectProperty<>(chbHasSubObjectives);
        });
        return new TableColumn[]{index, objective, completed, hasSubObjectives};
    }

    @Override
    protected void setGridControls() {

        grid.addNode("lblObjective", "Label", "Objective: ");
        grid.addNode("txtObjective", "TextField");
        grid.addRow("lblObjective", "txtObjective");

        grid.addNode("lblCompleted", "Label", "Completed: ");
        grid.addNode("chbCompleted", "CheckBox");
        grid.addRow("lblCompleted", "chbCompleted");

    }

    @Override
    protected void showDetails(GameObjective object) {
        controller.addToScenePath(new GameObjectiveDetailsPageController(object));
    }

    @Override
    protected GameObjective save() {

        String name = ((TextField) grid.getNode("txtObjective")).getText();
        boolean completed = ((CheckBox) grid.getNode("chbCompleted")).isSelected();

        GameObjective objective = new GameObjective(list.size() + 1, name, completed);
        if (parent != null) {
            parent.addObjectives(objective);
            controller.editEntity(parent);
            objective.setId(DomainHelper.getLast(parent.getObjectives()).getId());
        } else {
            entity.addObjectives(objective);
            controller.editEntity(entity);
            objective.setId(DomainHelper.getLast(entity.getObjectives()).getId());
        }

        grid.clearFields();
        
        return objective;
    }

    @Override
    protected void deleteObject(GameObjective object) {

        if (parent != null) {
            parent.removeObjectives(object);
            controller.editEntity(parent);
        } else {
            entity.removeObjectives(object);
            controller.editEntity(entity);
        }
        controller.deleteEntity(object);

    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="index methods">
    /**
     * Changes the Index of the Selected GameObjective
     *
     * @param up direction of the index
     * @param allTheWay to top/bottom of the list
     */
    private void changeIndex(boolean up, boolean allTheWay) {
        GameObjective gameObjective = getSelected();
        if (gameObjective == null) {
            return;
        }
        int index = gameObjective.getIndex();
        while ((up && index > 1) || (!up && index < list.size())) {
            changeIndex(gameObjective, index, up);
            if (!allTheWay) {
                break;
            }
            index = gameObjective.getIndex();
        }
        controller.editEntity(entity);
        sortByIndex();
    }

    /**
     * Changes the index of the passed GameObjective by 1
     *
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
    
    //</editor-fold>
    
}
