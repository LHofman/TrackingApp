package gui;

import domain.DomainController;
import domain.entity.GameObjective;
import domain.entity.IEntity;
import domain.item.Game;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EntityDetailsPageController extends GridPane {

    //<editor-fold defaultstate="collapsed" desc="variables">
    protected DomainController controller;
    protected IEntity entity;
    protected Class clazz;
    protected CustomGridPane grid;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructors">
    protected EntityDetailsPageController(Class clazz) {
        this.controller = DomainController.getInstance();
        this.clazz = clazz;

        grid = new CustomGridPane(this);
        setUpChildren();
    }

    public EntityDetailsPageController(IEntity entity) {
        this(entity.getClass());
        this.entity = entity;
        fillNodes();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GridPane">
    //<editor-fold defaultstate="collapsed" desc="setUp">
    /**
     * Fills the grid
     */
    private void setUpChildren() {
        //create children
        setUpForm();
        grid.addButtons(e -> save(), e -> closeStage());
        //constraints
        grid.setUpConstraints();
    }

    /**
     * Fills the grid with fields from {@link domain.item.Item}
     */
    protected void setUpForm() {
        if (clazz == GameObjective.class) {
            //objective
            grid.addNode("lblObjective", "Label", "Objective: ");
            grid.addNode("txtObjective", "TextField");
            grid.addRow("lblObjective", "txtObjective");
            //completed
            grid.addNode("lblCompleted", "Label", "Completed: ");
            grid.addNode("chbCompleted", "CheckBox");
            grid.addRow("lblCompleted", "chbCompleted");
            //sub-objectives
            grid.addNode("lblObjectives", "Label", "Objectives: ");
            grid.addNode("btnObjectives", "Button", "See Objectives");
            ((Button) grid.getNode("btnObjectives")).setOnAction(event -> {

                if (!saveEntity()){
                    controller.throwException("Please first fill in the title and date first");
                    return;
                }

                GameObjective gameObjective = (GameObjective) controller.getEntityFromId(GameObjective.class, ((IEntity)entity).getId());
                Game game = gameObjective.getGame();
                controller.addToScenePath(new GameObjectivesPageController(game, gameObjective));
            });
            grid.addRow("lblObjectives", "btnObjectives");
        }
    }
    
    //</editor-fold>
    /**
     * Saves the item to the database
     */
    private void save() {
        if (saveEntity()) {
            closeStage();
        }
    }

    /**
     * Fills in the form
     */
    protected void fillNodes() {
        if (entity.getClass() == GameObjective.class) {
            GameObjective objective = (GameObjective) entity;
            ((TextField) grid.getNode("txtObjective")).setText(objective.getObjective());
            ((CheckBox) grid.getNode("chbCompleted")).setSelected(objective.isCompleted());
        }
    }

    //</editor-fold>
    /**
     * Saves the entity to the db
     * @return whether the saving succeeded
     */
    protected boolean saveEntity() {
        try {
            if (entity.getClass() == GameObjective.class) {
                String objective = ((TextField)grid.getNode("txtObjective")).getText();
                boolean completed = ((CheckBox)grid.getNode("chbCompleted")).isSelected();
                
                GameObjective gameObjective = (GameObjective)entity;
                gameObjective.setObjective(objective);
                gameObjective.setCompleted(completed);
                
                controller.editEntity(gameObjective.getGame());
                controller.notifyObservers(gameObjective);
            }
            return true;
        } catch (Exception e) {
            controller.throwException(e);
            return false;
        }
    }

    /**
     * Closes the stage
     */
    private void closeStage() {
        controller.removeSceneFromPath();
    }

}
