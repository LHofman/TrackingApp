package gui;

import domain.entity.GameObjective;
import domain.entity.IEntity;
import domain.item.Game;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GameObjectiveDetailsPageController extends EntityDetailsPageController<GameObjective> {

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public GameObjectiveDetailsPageController() {
        super(GameObjective.class);
    }

    public GameObjectiveDetailsPageController(GameObjective gameObjective) {
        super(gameObjective);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GridPane">
    //<editor-fold defaultstate="collapsed" desc="setUp">
    /**
     * Fills the grid
     */
    @Override
    protected void setUpForm() {
        //objective
        grid.addNode("lblObjective", "Label", "Objective: ");
        grid.addNode("txtObjective", "TextField");
        grid.addRow("lblObjective", "txtObjective");
        //completed
        grid.addNode("lblCompleted", "Label", "Completed: ");
        grid.addNode("chbCompleted", "CheckBox");
        grid.addRow("lblCompleted", "chbCompleted");
        //links
        grid.addNode("lblLinks", "Label", "Links: ");
        grid.addNode("lsvLinks", "ListView");
        grid.addNode("btnOpenLink", "Button", "Open Link");
        ((Button) grid.getNode("btnOpenLink")).setOnAction(event -> {
            String link = ((ListView<String>) grid.getNode("lsvLinks")).getSelectionModel().getSelectedItem();
            if (link != null) {
                entity.openLink(link);
            }
        });
        grid.addNode("btnRemoveLink", "Button", "Remove Link");
        ((Button) grid.getNode("btnRemoveLink")).setOnAction(event -> {
            String link = ((ListView<String>) grid.getNode("lsvLinks")).getSelectionModel().getSelectedItem();
            if (link != null) {
                ((ListView<String>) grid.getNode("lsvLinks")).getItems().remove(link);
                entity.removeLinks(link);
            }
        });
        grid.addPane("vboxLinkButtons", new VBox(), "btnOpenLink", "btnRemoveLink");
        grid.addRow("lblLinks", "lsvLinks", "vboxLinkButtons");
        grid.addNode("lblAddLink", "Label", "Add Link: ");
        grid.addNode("txtLinkName", "TextField", "Name");
        grid.addNode("txtLinkUrl", "TextField", "Link");
        grid.addNode("btnAddLink", "Button", "Add Link");
        ((Button) grid.getNode("btnAddLink")).setOnAction(event -> {
            String name = ((TextField) grid.getNode("txtLinkName")).getText();
            String link = ((TextField) grid.getNode("txtLinkUrl")).getText();
            entity.addLink(name, link);
            ((ListView<String>) grid.getNode("lsvLinks")).getItems().add(name);
        });
        grid.addRow("lblAddLink", "txtLinkName", "txtLinkUrl", "btnAddLink");
        //sub-objectives
        grid.addNode("lblObjectives", "Label", "Objectives: ");
        grid.addNode("btnObjectives", "Button", "See Objectives");
        ((Button) grid.getNode("btnObjectives")).setOnAction(event -> {

            if (!saveEntity()) {
                controller.throwException("Please first fill in the title and date first");
                return;
            }

            GameObjective gameObjective = (GameObjective) controller.getEntityFromId(GameObjective.class, ((IEntity) entity).getId());
            Game game = gameObjective.getGame();
            controller.addToScenePath(new GameObjectivesPageController(game, gameObjective));
        });
        grid.addRow("lblObjectives", "btnObjectives");
    }

    //</editor-fold>
    /**
     * Fills in the form
     */
    @Override
    protected void fillNodes() {
        ((TextField) grid.getNode("txtObjective")).setText(entity.getObjective());
        ((CheckBox) grid.getNode("chbCompleted")).setSelected(entity.isCompleted());
        ((ListView) grid.getNode("lsvLinks")).setItems(FXCollections.observableList(new ArrayList<>(entity.getLinks().keySet())));
    }

    //</editor-fold>
    /**
     * Saves the Item to the db
     *
     * @return Whether the saving succeeded
     */
    @Override
    protected boolean saveEntity() {
        try {
            String objective = ((TextField) grid.getNode("txtObjective")).getText();
            boolean completed = ((CheckBox) grid.getNode("chbCompleted")).isSelected();

            entity.setObjective(objective);
            entity.setCompleted(completed);

            controller.editEntity(entity);

            return true;
        } catch (Exception e) {
            controller.throwException(e);
            return false;
        }
    }

}
