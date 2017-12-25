package gui;

import domain.DomainController;
import domain.DomainHelper;
import domain.MyDate;
import domain.Person;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.item.Book;
import domain.item.Item;
import domain.item.ItemFactory;
import java.sql.Date;
import java.util.Calendar;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ItemDetailsPageController extends GridPane {

    //<editor-fold defaultstate="collapsed" desc="variables">
    private DomainController controller;
    private Item item;
    private CustomGridPane grid;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public ItemDetailsPageController() {
        this.controller = DomainController.getInstance();

        grid = new CustomGridPane(this);
        setUpChildren();
    }

    public ItemDetailsPageController(Item item) {
        this();
        this.item = item;
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
        ChoiceBox cbType = addType();
        addBeforeDefaults();
        addDefaultChildren();
        grid.addButtons(e -> save(), e -> closeStage());
        //set type when everything exists
        cbType.getSelectionModel().select(0);
        //constraints
        grid.setUpConstraints(600, 400, 3);
    }

    /**
     * @return A ComboBox for the {@link domain.enums.ItemType}
     */
    private ChoiceBox addType() {
        addNode("cbType", "ChoiceBox", (Object[]) ItemType.values());
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

        });
        grid.addRow("empty", "cbType");
        return cbType;
    }

    /**
     * Adds all controls above the defaults
     */
    private void addBeforeDefaults() {
        addNode("lblAuthor", "Label", "Author: ");
        addNode("cmbAuthor", "ComboBox", (Object[]) controller.getAll(Person.class).toArray());
        new AutoCompleteComboBoxListener<Person>((ComboBox) getNode("cmbAuthor"));
        addNode("btnNewAuthor", "Button", "New Author");
        //<editor-fold defaultstate="collapsed" desc="new author pane">
        ((Button) getNode("btnNewAuthor")).setOnAction(event -> {
            Stage newAuthorStage = new Stage(StageStyle.DECORATED);
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
                    ((ComboBox) getNode("cmbAuthor")).getItems().add(newPerson);
                    newAuthorStage.close();
                } catch (Exception e) {
                    controller.throwException(e);
                }
            }, cancelEvent -> {
                newAuthorStage.close();
            });
            newAuthorGrid.setUpConstraints(width, height - 50, 2);

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
        addNode("lblTitle", "Label", "Title: ");
        addNode("txtTitle", "TextField");
        grid.addRow("lblTitle", "txtTitle");
        //releasedate
        addNode("lblReleaseDate", "Label", "ReleaseDate: ");
        addNode("dpReleaseDate", "DatePicker");
        grid.addRow("lblReleaseDate", "dpReleaseDate");
        DatePicker releaseDate = (DatePicker) getNode("dpReleaseDate");
        releaseDate.setOnAction((ActionEvent event) -> {
            Date release = Date.valueOf(releaseDate.getValue());
            boolean after = release.after(Calendar.getInstance().getTime());
            CheckBox inPossession = (CheckBox) getNode("chbInPossession");
            ChoiceBox state = (ChoiceBox) getNode("cbStatus");
            if (after) {
                inPossession.setSelected(false);
                state.getSelectionModel().select(state.getItems().get(0));
            }
            grid.setDisable(after, "chbInPossession", "cbStatus");
        });
        //in possession
        addNode("lblInPossession", "Label", "In Possession: ");
        addNode("chbInPossession", "CheckBox");
        grid.addRow("lblInPossession", "chbInPossession");
        //state
        addNode("lblStatus", "Label", "Status: ");
        addNode("cbStatus", "ChoiceBox", (Object[]) Status.values());
        ((ChoiceBox) grid.getNode("cbStatus")).getSelectionModel().select(0);
        grid.addRow("lblStatus", "cbStatus");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="button actions">
    /**
     * Saves the item to the database
     */
    private void save() {
        Item i;
        try {
            ItemType type = (ItemType) ((ChoiceBox) getNode("cbType")).getSelectionModel().getSelectedItem();
            String title = ((TextField) getNode("txtTitle")).getText();
            MyDate releaseDate = new MyDate(((DatePicker) getNode("dpReleaseDate")).getValue());
            boolean inCollection = ((CheckBox) getNode("chbInPossession")).isSelected();
            Status status = (Status) ((ChoiceBox) getNode("cbStatus")).getSelectionModel().getSelectedItem();

            switch (type) {
                case Book:
                    ComboBox cmbAuthor = (ComboBox) getNode("cmbAuthor");
                    Person author = (Person) cmbAuthor.getItems().get(cmbAuthor.getSelectionModel().getSelectedIndex());
                    
                    i = getItemToSave(new Book(title, releaseDate, author, inCollection, status));
                    break;
                default:
                    i = getItemToSave(ItemFactory.createItem(type, title, releaseDate, inCollection, status));
            }
        } catch (Exception e) {
            controller.throwException(e);
            return;
        }

        if (item == null) {
            controller.addEntity(i);
        } else {
            controller.editEntity(i);
        }

        closeStage();
    }

    /**
     * Returns the actual item
     *
     * @param i
     * @return
     */
    private Item getItemToSave(Item i) {
        if (item == null) {
            return i;
        } else {
            item.editItem(i);
            return item;
        }
    }

    //</editor-fold>
    /**
     * Adds a node to the grid
     *
     * @param key
     * @param type
     * @param values
     */
    private void addNode(String key, String type, Object... values) {
        grid.addNode(key, GUIHelper.createControl(type, values));
    }

    /**
     * Fills in the form
     */
    private void fillNodes() {

        //disable type change
        grid.setDisable(true, "cbType");

        fillDefaultNodes();
        //TODO author if (iClass == Book.class) ((TextField)getNode("txtAuthor")).setText(((Book)item).getAuthor());

    }

    /**
     * Fills in the form with all the fields from {@link domain.item.Item}
     */
    private void fillDefaultNodes() {
        ((ChoiceBox) getNode("cbType")).getSelectionModel().select(item.getType());
        ((TextField) getNode("txtTitle")).setText(item.getTitle());
        MyDate cal = item.getReleaseDate();
        ((DatePicker) getNode("dpReleaseDate")).setValue(cal.getDateAsLocalDate());
        ((CheckBox) getNode("chbInPossession")).setSelected(item.isInCollection());
        ((ChoiceBox) getNode("cbStatus")).getSelectionModel().select(item.getStatus());
    }

    /**
     * @param key
     * @return a node from the grid
     */
    private Node getNode(String key) {
        return grid.getNode(key);
    }

    //</editor-fold>
    /**
     * Closes the stage
     */
    private void closeStage() {
        controller.removeSceneFromPath();
    }

}
