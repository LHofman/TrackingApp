package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * Helper class for the GUI
 */
public abstract class GUIHelper {

    public static int STAGEHEIGHT = 750, STAGEWIDTH = 1200;
    
    /**
     * Helper method for loading fxml files
     * @param fileName
     * @param root 
     */
    public static void loadFXML(String fileName, Object root) {
        try{
            FXMLLoader loader = new FXMLLoader(root.getClass().getResource(fileName.concat(".fxml")));
            loader.setRoot(root);
            loader.setController(root);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Helper method for showing a default alert
     * @param type
     * @param title
     * @param content 
     * @return confirmation
     */
    public static boolean createAlert(AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setHeaderText(title);
        alert.setContentText(content);
        if (type==AlertType.CONFIRMATION)
            return alert.showAndWait().get()==ButtonType.OK;
        else {
            alert.show();
            return true;
        }
    }

    /**
     * @param <E>
     * @param values
     * @return A list with "---No Filter---" as the first value
     */
    public static <E> String[] addNoFilter(List<E> values){
        List<String> list = new ArrayList<>();
        list.add("---No Filter---");
        values.stream().forEach(v -> list.add(v.toString()));
        return list.toArray(new String[list.size()]);
    }
    /**
     * @param <E>
     * @param values
     * @return A list with "---No Filter---" as the first value
     */
    public static <E> String[] addNoFilter(E[] values){
        return addNoFilter(Arrays.asList(values));
    }
    
    //<editor-fold defaultstate="collapsed" desc="create controls">
    
    private static final Font DEFAULT_FONT = Font.font(20);
    private static final double DEFAULT_WIDTH = 300;
    
    /**
     * @param type
     * @param values
     * @return A Control based on the specified type
     */
    public static Node createControl(String type, Object... values){
        String value = "";
        if (values.length>0) value = values[0].toString();
        switch(type){
            case "Button": return createButton(value);
            case "CheckBox": return createCheckBox(value);
            case "ChoiceBox": return new ChoiceBox(FXCollections.observableArrayList(values));
            case "ComboBox": return new ComboBox(FXCollections.observableArrayList(values));
            case "DatePicker": return new DatePicker();
            case "Label": return createLabel(value);
            case "TextField": return createTextField();
        }
        return null;
    }
    /**
     * @return A label without text
     */
    public static Node createEmptyControl(){
        return createControl("Label", "");
    }
    /**
     * @param text
     * @return A label
     */
    private static Label createLabel(String text){
        Label label = new Label(text);
        label.setFont(DEFAULT_FONT);
        return label;
    }
    /**
     * @param text
     * @return A Button
     */
    private static Button createButton(String text){
        Button button = new Button(text);
        button.setFont(DEFAULT_FONT);
        button.setDefaultButton(true);
        return button;
    }
    /**
     * @param text
     * @return A Checkbox
     */
    private static CheckBox createCheckBox(String text){
        CheckBox checkBox = new CheckBox(text);
        checkBox.setFont(DEFAULT_FONT);
        return checkBox;
    }
    /**
     * @return A TextField
     */
    private static TextField createTextField(){
        TextField textField = new TextField();
        textField.setFont(new Font(15));
        textField.setMaxWidth(DEFAULT_WIDTH);
        return textField;
    }
    
    //</editor-fold>

}
