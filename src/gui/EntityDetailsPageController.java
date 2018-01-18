package gui;

import domain.DomainController;
import domain.MyDate;
import domain.entity.Episode;
import domain.entity.GameObjective;
import domain.entity.IEntity;
import domain.item.Game;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EntityDetailsPageController<E extends IEntity> extends GridPane {

    //<editor-fold defaultstate="collapsed" desc="variables">
    protected DomainController controller;
    protected E entity;
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

    public EntityDetailsPageController(E entity) {
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

                if (!saveEntity()) {
                    controller.throwException("Please first fill in the title and date first");
                    return;
                }

                GameObjective gameObjective = (GameObjective) controller.getEntityFromId(GameObjective.class, ((IEntity) entity).getId());
                Game game = gameObjective.getGame();
                controller.addToScenePath(new GameObjectivesPageController(game, gameObjective));
            });
            grid.addRow("lblObjectives", "btnObjectives");
        } else if (clazz == Episode.class) {
            grid.addNode("lblSeason", "Label", "Season: ");
            grid.addNode("txtSeason", "TextField");
            grid.addRow("lblSeason", "txtSeason");

            grid.addNode("lblEpisodeNr", "Label", "Episode Nr: ");
            grid.addNode("txtEpisodeNr", "TextField");
            grid.addRow("lblEpisodeNr", "txtEpisodeNr");

            grid.addNode("lblTitle", "Label", "Title: ");
            grid.addNode("txtTitle", "TextField");
            grid.addRow("lblTitle", "txtTitle");

            grid.addNode("lblReleaseDate", "Label", "ReleaseDate: ");
            grid.addNode("dpReleaseDate", "DatePicker");
            grid.addRow("lblReleaseDate", "dpReleaseDate");

            grid.addNode("lblWatched", "Label", "Watched: ");
            grid.addNode("chbWatched", "CheckBox");
            grid.addRow("lblWatched", "chbWatched");

            grid.addNode("lblCollected", "Label", "Collected: ");
            grid.addNode("chbCollected", "CheckBox");
            grid.addRow("lblCollected", "chbCollected");
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
        }else if (entity.getClass() == Episode.class){
            Episode episode = (Episode) entity;
            ((TextField)grid.getNode("txtSeason")).setText(""+episode.getSeason());
            ((TextField)grid.getNode("txtEpisodeNr")).setText(""+episode.getEpisodeNr());
            ((TextField)grid.getNode("txtTitle")).setText(episode.getTitle());
            ((DatePicker)grid.getNode("dpReleaseDate")).setValue(episode.getReleaseDate().getDateAsLocalDate());
            ((CheckBox)grid.getNode("chbWatched")).setSelected(episode.isWatched());
            ((CheckBox)grid.getNode("chbCollected")).setSelected(episode.isInCollection());
        }
    }

    //</editor-fold>
    /**
     * Saves the entity to the db
     *
     * @return whether the saving succeeded
     */
    protected boolean saveEntity() {
        try {
            if (entity.getClass() == GameObjective.class) {
                String objective = ((TextField) grid.getNode("txtObjective")).getText();
                boolean completed = ((CheckBox) grid.getNode("chbCompleted")).isSelected();

                GameObjective gameObjective = (GameObjective) entity;
                gameObjective.setObjective(objective);
                gameObjective.setCompleted(completed);

                controller.editEntity(gameObjective.getGame());
            }else if (entity.getClass() == Episode.class){
                int season = Integer.parseInt(((TextField)grid.getNode("txtSeason")).getText());
                int episodeNr = Integer.parseInt(((TextField)grid.getNode("txtEpisodeNr")).getText());
                String title = ((TextField)grid.getNode("txtTitle")).getText();
                MyDate releaseDate = new MyDate(((DatePicker)grid.getNode("dpReleaseDate")).getValue());
                boolean watched = ((CheckBox)grid.getNode("chbWatched")).isSelected();
                boolean collected = ((CheckBox)grid.getNode("chbCollected")).isSelected();
                
                Episode episode = (Episode) entity;
                episode.editEpisode(new Episode(season, episodeNr, title, releaseDate, watched, collected));
                
                controller.editEntity(episode.getTvShow());                
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
