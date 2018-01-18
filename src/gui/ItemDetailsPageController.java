package gui;

import domain.DomainHelper;
import domain.MyDate;
import domain.entity.Person;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.item.Book;
import domain.item.Game;
import domain.item.Item;
import domain.item.ItemFactory;
import domain.item.TvShow;
import java.sql.Date;
import java.util.Calendar;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ItemDetailsPageController extends EntityDetailsPageController<Item> {

    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public ItemDetailsPageController() {super(Item.class);}
    public ItemDetailsPageController(Item item) {super(item);}

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GridPane">
    //<editor-fold defaultstate="collapsed" desc="setUp">
    
    /**
     * Fills the grid
     */
    @Override
    protected void setUpForm() {
        //create children
        ChoiceBox cbType = addType();
        addBeforeDefaults();
        addDefaultChildren();
        addAfterDefaults();
        //set type when everything exists
        cbType.getSelectionModel().select(0);
    }

    /**
     * @return A ComboBox for the {@link domain.enums.ItemType}
     */
    private ChoiceBox addType() {
        grid.addNode("cbType", "ChoiceBox", (Object[]) ItemType.values());
        ChoiceBox cbType = (ChoiceBox) grid.getNode("cbType");
        cbType.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            ItemType value = (ItemType) newValue;
            //set available statuses
            ChoiceBox cbStatus = (ChoiceBox) grid.getNode("cbStatus");
            cbStatus.setItems(FXCollections.observableArrayList(
                    DomainHelper.getAvailableStatuses(value)));
            cbStatus.getSelectionModel().select(0);
            //set author (in)visible
            grid.setVisible(newValue == ItemType.Book, "lblAuthor", "cmbAuthor", "btnNewAuthor");
            //set objectives (in)visible
            boolean visible = false;
            String lblChildren = "Children", btnChildren = "Children";
            if (newValue==ItemType.Game){
                visible = true;
                lblChildren = "Objectives: ";
                btnChildren = "See Objectives";
            }else if (newValue==ItemType.TvShow){
                visible = true;
                lblChildren = "Seasons: ";
                btnChildren = "See Seasons";
            }
            if (visible){
                grid.setVisible(true, "lblObjectives", "btnObjectives");
                ((Label)grid.getNode("lblChildren")).setText(lblChildren);
                ((Button)grid.getNode("btnChildren")).setText(btnChildren);
            }
            
        });
        grid.addRow("empty", "cbType");
        return cbType;
    }

    /**
     * Adds all controls above the defaults
     */
    private void addBeforeDefaults() {
        grid.addNode("lblAuthor", "Label", "Author: ");
        grid.addNode("cmbAuthor", "ComboBox", (Object[]) controller.getAll(Person.class).toArray());
        new AutoCompleteComboBoxListener<Person>((ComboBox) grid.getNode("cmbAuthor"));
        grid.addNode("btnNewAuthor", "Button", "New Author");
        //<editor-fold defaultstate="collapsed" desc="new author pane">
        ((Button) grid.getNode("btnNewAuthor")).setOnAction(event -> {
            Stage newAuthorStage = new Stage(StageStyle.DECORATED);
            newAuthorStage.setTitle("New Author");
            int width = 600;
            int height = 300;
            newAuthorStage.setWidth(width);
            newAuthorStage.setHeight(height);

            GridPane newAuthorPane = new GridPane();
            CustomGridPane newAuthorGrid = new CustomGridPane(newAuthorPane);
            newAuthorGrid.addNode("lblFirstName", GUIHelper.createControl("Label", "First Name: "));
            newAuthorGrid.addNode("txtFirstName", GUIHelper.createControl("TextField"));
            newAuthorGrid.addRow("lblFirstName", "txtFirstName");
            newAuthorGrid.addNode("lblName", GUIHelper.createControl("Label", "Surname: "));
            newAuthorGrid.addNode("txtName", GUIHelper.createControl("TextField"));
            newAuthorGrid.addRow("lblName", "txtName");
            newAuthorGrid.addButtons(saveEvent -> {
                try {
                    String name = ((TextField) newAuthorGrid.getNode("txtName")).getText();
                    String firstName = ((TextField) newAuthorGrid.getNode("txtFirstName")).getText();
                    Person newPerson = new Person(name, firstName);
                    controller.addEntity(newPerson);
                    ((ComboBox) grid.getNode("cmbAuthor")).getItems().add(newPerson);
                    newAuthorStage.close();
                } catch (Exception e) {
                    controller.throwException(e);
                }
            }, cancelEvent -> {
                newAuthorStage.close();
            });
            newAuthorGrid.setUpConstraints();

            Scene scene = new Scene(newAuthorPane);
            newAuthorStage.setScene(scene);
            newAuthorStage.show();
        });
        //</editor-fold>
        grid.addRow("lblAuthor", "cmbAuthor", "btnNewAuthor");
    }

    /**
     * Fills the grid with fields from {@link domain.item.Item}
     */
    private void addDefaultChildren() {
        //title
        grid.addNode("lblTitle", "Label", "Title: ");
        grid.addNode("txtTitle", "TextField");
        grid.addRow("lblTitle", "txtTitle");
        //releasedate
        grid.addNode("lblReleaseDate", "Label", "ReleaseDate: ");
        grid.addNode("dpReleaseDate", "DatePicker");
        grid.addRow("lblReleaseDate", "dpReleaseDate");
        DatePicker releaseDate = (DatePicker) grid.getNode("dpReleaseDate");
        releaseDate.setOnAction((ActionEvent event) -> {
            Date release = Date.valueOf(releaseDate.getValue());
            boolean after = release.after(Calendar.getInstance().getTime());
            CheckBox inPossession = (CheckBox) grid.getNode("chbInPossession");
            ChoiceBox state = (ChoiceBox) grid.getNode("cbStatus");
            if (after) {
                inPossession.setSelected(false);
                state.getSelectionModel().select(state.getItems().get(0));
            }
            grid.setDisable(after, "chbInPossession", "cbStatus");
        });
        //in possession
        grid.addNode("lblInPossession", "Label", "In Possession: ");
        grid.addNode("chbInPossession", "CheckBox");
        grid.addRow("lblInPossession", "chbInPossession");
        //state
        grid.addNode("lblStatus", "Label", "Status: ");
        grid.addNode("cbStatus", "ChoiceBox", (Object[]) Status.values());
        ((ChoiceBox) grid.getNode("cbStatus")).getSelectionModel().select(0);
        grid.addRow("lblStatus", "cbStatus");
    }

    /**
     * Adds all controls below the defaults
     */
    private void addAfterDefaults() {
        grid.addNode("lblChildren", "Label", "Children: ");
        grid.addNode("btnChildren", "Button", "See Children");
        ((Button) grid.getNode("btnChildren")).setOnAction(event -> {

            if (!saveEntity()){
                controller.throwException("Please first fill in the title and date first");
                return;
            }

            switch(entity.getType()){
                case Game: 
                    Game game = (Game) controller.getEntityFromId(Game.class, entity.getId());
                    controller.addToScenePath(new GameObjectivesPageController(game));
                    break;
                case TvShow:
                    TvShow tvShow = (TvShow) controller.getEntityFromId(TvShow.class, entity.getId());
                    controller.addToScenePath(new TvShowSeasonsPageController(tvShow));
                    break;
            }
            
        });
        grid.addRow("lblChildren", "btnChildren");
    }

    //</editor-fold>
    /**
     * Fills in the form
     */
    @Override
    protected void fillNodes() {

        //disable type change
        grid.setDisable(true, "cbType");

        fillDefaultNodes();
        if (entity.getClass() == Book.class) ((ComboBox)grid.getNode("cmbAuthor")).getSelectionModel().select(((Book)entity).getAuthor());

    }

    /**
     * Fills in the form with all the fields from {@link domain.item.Item}
     */
    private void fillDefaultNodes() {
        Item item = (Item)entity;
        ((ChoiceBox) grid.getNode("cbType")).getSelectionModel().select(item.getType());
        ((TextField) grid.getNode("txtTitle")).setText(item.getTitle());
        MyDate cal = item.getReleaseDate();
        ((DatePicker) grid.getNode("dpReleaseDate")).setValue(cal.getDateAsLocalDate());
        ((CheckBox) grid.getNode("chbInPossession")).setSelected(item.isInCollection());
        ((ChoiceBox) grid.getNode("cbStatus")).getSelectionModel().select(item.getStatus());
    }

    //</editor-fold>
    
    /**
     * Saves the Item to the db
     * @return Whether the saving succeeded
     */
    @Override
    protected boolean saveEntity() {
        Item i;
        try {
            ItemType type = (ItemType) ((ChoiceBox) grid.getNode("cbType")).getSelectionModel().getSelectedItem();
            String title = ((TextField) grid.getNode("txtTitle")).getText();
            MyDate releaseDate = new MyDate(((DatePicker) grid.getNode("dpReleaseDate")).getValue());
            boolean inCollection = ((CheckBox) grid.getNode("chbInPossession")).isSelected();
            Status status = (Status) ((ChoiceBox) grid.getNode("cbStatus")).getSelectionModel().getSelectedItem();

            switch (type) {
                case Book:
                    ComboBox cmbAuthor = (ComboBox) grid.getNode("cmbAuthor");
                    Person author = (Person) cmbAuthor.getItems().get(cmbAuthor.getSelectionModel().getSelectedIndex());

                    i = getItemToSave(new Book(title, releaseDate, author, inCollection, status));
                    break;
                default:
                    i = getItemToSave(ItemFactory.createItem(type, title, releaseDate, inCollection, status));
            }

            if (entity == null) {
                controller.addEntity(i);
            } else {
                controller.editEntity(i);
            }

            return true;
        } catch (Exception e) {
            controller.throwException(e);
            return false;
        }
    }

    /**
     * Returns the actual item
     *
     * @param i
     * @return
     */
    private Item getItemToSave(Item i) {
        if (entity == null) {
            return i;
        } else {
            Item item = (Item)entity;
            item.editItem(i);
            return item;
        }
    }

}
