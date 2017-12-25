package gui;

import domain.DomainController;
import domain.Person;
import domain.enums.ItemType;
import domain.enums.Status;
import java.util.HashMap;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Custom grid made for filters
 */
public class CustomFilterGrid extends CustomGridPane {

    private final DomainController controller;

    //<editor-fold defaultstate="collapsed" desc="constructor">
    
    public CustomFilterGrid(GridPane grid) {
        super(grid);
        this.controller = DomainController.getInstance();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    /**
     * Adds all the filters Give the filters the same name as the switch cases
     * from {@link domain.DomainController#changeFilter(java.util.Map) }
     *
     * @param filters
     */
    public void addFilters(String... filters) {
        for (String filter : filters) {
            switch (filter) {
                case "Type":
                    addNode("lblType", "Label", "Type: ");
                    addNode("cbType", "ChoiceBox", (Object[])GUIHelper.addNoFilter(ItemType.values()));
                    ((ChoiceBox)getNode("cbType")).getSelectionModel().selectedItemProperty()
                            .addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                        changeFilter();
                        setDisable(!newValue.equals(ItemType.Book.toString()), "cmbAuthor");
                    });
                    addRow("lblType", "cbType");
                    break;
                case "Author":
                    addNode("lblAuthor", "Label", "Author: ");
                    addNode("cmbAuthor", "ComboBox", (Object[])GUIHelper.addNoFilter(controller.getAll(Person.class)));
                    new AutoCompleteComboBoxListener((ComboBox)getNode("cmbAuthor"));
                    addRow("lblAuthor", "cmbAuthor");
                    break;
                case "Title":
                    addNode("lblTitle", "Label", "Title: ");
                    addNode("txtTitle", "TextField");
                    addRow("lblTitle", "txtTitle");
                    break;
                case "ReleaseDateStart":
                    addNode("lblReleaseDate", "Label", "ReleaseDate: ");
                    addNode("dpReleaseDateStart", "DatePicker");
                    Button btnClearReleaseDateStart = (Button) GUIHelper.createControl("Button", "Clear");
                    btnClearReleaseDateStart.setOnAction(event -> ((DatePicker)getNode("dpReleaseDateStart")).setValue(null));
                    addNode("btnClearReleaseDateStart", btnClearReleaseDateStart);
                    addRow("lblReleaseDate", "dpReleaseDateStart", "btnClearReleaseDateStart");
                    break;
                case "ReleaseDateEnd":
                    addNode("dpReleaseDateEnd", "DatePicker");
                    Button btnClearReleaseDateEnd = (Button) GUIHelper.createControl("Button", "Clear");
                    btnClearReleaseDateEnd.setOnAction(event -> ((DatePicker)getNode("dpReleaseDateEnd")).setValue(null));
                    addNode("btnClearReleaseDateEnd", btnClearReleaseDateEnd);
                    addRow("empty", "dpReleaseDateEnd", "btnClearReleaseDateEnd");
                    break;
                case "InCollection":
                    addNode("lblInCollection", "Label", "In Collection: ");
                    addNode("chbInCollection", "CheckBox");
                    addNode("chbInCollectionFilter", "CheckBox", "Filter On/Off");
                    addPane("hboxInCollection", new HBox(), "chbInCollection", "chbInCollectionFilter");
                    ((HBox) getNode("hboxInCollection")).setAlignment(Pos.CENTER);
                    addRow("lblInCollection", "hboxInCollection");
                    break;
                case "Status":
                    addNode("lblStatus", "Label", "Status: ");
                    addNode("cbStatus", "ChoiceBox", (Object[])GUIHelper.addNoFilter(Status.values()));
                    addRow("lblStatus", "cbStatus");
                    break;
            }
        }
        //add buttons
        //clear filters
        Button btnClearFilters = (Button) GUIHelper.createControl("Button", "Clear Filters");
        btnClearFilters.setOnAction((ActionEvent event) -> clearFilters());
        addNode("btnClearFilters", btnClearFilters);
        //cancel
        Button btnCancel = (Button) GUIHelper.createControl("Button", "Cancel");
        btnCancel.setOnAction(event -> btnCancel.getScene().getWindow().hide());
        addNode("btnCancel", btnCancel);
        addRow("empty", "btnClearFilters", "btnCancel");
        
        clearFilters();
    }

    /**
     * Adds a node to the grid Currently filters:
     * <ul>
     * <li>ChoiceBox</li>
     * <li>TextField</li>
     * <li>CheckBox</li>
     * </ul>
     *
     * @param key
     * @param value
     */
    private void addNode(String key, String type, Object... values) {
        EventHandler changeFilterEvent = event -> {changeFilter();};
        Node node = GUIHelper.createControl(type, values);
        switch (node.getClass().getName()) {
            case "javafx.scene.control.ChoiceBox":
                ((ChoiceBox) node).setOnAction(changeFilterEvent);
                break;
            case "javafx.scene.control.TextField":
                ((TextField) node).setOnKeyReleased(changeFilterEvent);
                break;
            case "javafx.scene.control.CheckBox":
                ((CheckBox) node).setOnAction(changeFilterEvent);
                break;
            case "javafx.scene.control.DatePicker":
                ((DatePicker) node).setOnAction(changeFilterEvent);
                break;
            case "javafx.scene.control.ComboBox":
                ((ComboBox) node).setOnAction(changeFilterEvent);
        }

        nodes.put(key, node);
    }

    /**
     * Clears the filters
     */
    public void clearFilters() {
        nodes.keySet().forEach((key -> {
            Object value = nodes.get(key);
            switch (value.getClass().getName()) {
                case "javafx.scene.control.ChoiceBox":
                    ((ChoiceBox) value).getSelectionModel().select(0);
                    break;
                case "javafx.scene.control.TextField":
                    ((TextField) value).setText("");
                    break;
                case "javafx.scene.control.DatePicker":
                    ((DatePicker) value).setValue(null);
                    break;
                case "javafx.scene.control.ComboBox":
                    ((ComboBox) value).getSelectionModel().select(0);
                    break;
                
            }
            if (key.equals("chbInCollectionFilter")) {
                ((CheckBox) value).setSelected(false);
            }
        }));
        changeFilter();
    }

    /**
     * Filters the items
     */
    private void changeFilter() {
        controller.changeFilter(new HashMap<String, Object>() {{
                nodes.keySet().forEach((key -> {
                    switch (key) {
                        case "cbType":
                            put("Type", ((ChoiceBox) nodes.get(key)).getSelectionModel().getSelectedItem());
                            break;
                        case "cmbAuthor":
                            put("Author", ((ComboBox)nodes.get(key)).getSelectionModel().getSelectedItem());
                            break;
                        case "txtTitle":
                            put("Title", ((TextField) nodes.get(key)).getText());
                            break;
                        case "dpReleaseDateStart":
                            put("ReleaseDateStart", ((DatePicker) nodes.get(key)).getValue());
                            break;
                        case "dpReleaseDateEnd":
                            put("ReleaseDateEnd", ((DatePicker) nodes.get(key)).getValue());
                            break;
                        case "chbInCollection":
                            put("InCollection", ((CheckBox) nodes.get(key)).isSelected());
                            break;
                        case "chbInCollectionFilter":
                            put("InCollectionFilter", ((CheckBox) nodes.get(key)).isSelected());
                            break;
                        case "cbStatus":
                            put("Status", ((ChoiceBox) nodes.get(key)).getSelectionModel().getSelectedItem());
                            break;
                    }
                }));
        }});
    }

    //</editor-fold>
    
}
