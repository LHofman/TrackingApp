package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

/**
 * Helper class to create a custom gridpane
 * default rownheight = 50
 */
public class CustomGridPane {
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    protected final GridPane grid;
    protected final Map<String, Node> nodes;
    protected int rows = 0;
    protected static int ROWHEIGHT;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public CustomGridPane(GridPane grid){
        this.grid = grid;
        nodes = new HashMap();
        rows = 0;
        this.grid.setHgap(50);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="nodes methods">
    
    /**
     * add a node to the gridpane
     * @param key
     * @param value 
     */
    public void addNode(String key, Node value){nodes.put(key, value);}
    /**
     * @param key
     * @return a node from this gridpane
     */
    public Node getNode(String key){
        if (key.equals("empty")) return GUIHelper.createEmptyControl();
        return nodes.get(key);
    }
    
    /**
     * put nodes from this gridpane inside another pane
     * @param key
     * @param pane
     * @param nodesToAdd 
     */
    public void addPane(String key, Pane pane, String... nodesToAdd){
        pane.getChildren().addAll(getNodes(nodesToAdd));
        nodes.put(key, pane);
    }
    
    /**
     * @param nodes
     * @return nodes from this gridpane
     */
    public List<Node> getNodes(String... nodes){
        List<Node> list = new ArrayList<>();
        for (String node: nodes)
            list.add(getNode(node));
        return list;
    }
    
    /**
     * Adds save and cancel button
     * @param save
     * @param cancel
     */
    public void addButtons(EventHandler save, EventHandler cancel) {
        //Save button
        Button btnSave = (Button)GUIHelper.createControl("Button", "Save");
        btnSave.setOnAction(save);
        addNode("btnSave", btnSave);
        //Cancel button
        Button btnCancel = (Button)GUIHelper.createControl("Button", "Cancel");
        btnCancel.setOnAction(cancel);
        addNode("btnCancel", btnCancel);
        //Add to the grid
        addRow("btnSave", "btnCancel");
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="grid methods">
    
    /**
     * Adds a row to the gridpane
     * @param nodes 
     */
    public void addRow(String... nodes){
        int length = nodes.length;
        while(length > grid.getColumnConstraints().size())
            grid.getColumnConstraints().add(new ColumnConstraints());
        grid.getRowConstraints().add(new RowConstraints(ROWHEIGHT));
                
        grid.addRow(rows++, getNodes(nodes).toArray(new Node[length]));
    }
    /**
     * Adds a row to the gridpane
     * @param height
     * @param nodes 
     */
    public void addRow(int height, String... nodes){
        int temp = ROWHEIGHT;
        ROWHEIGHT = height;
        addRow(nodes);
        ROWHEIGHT = temp;
    }
    
    /**
     * Sets the constraints of the grid
     * @param width
     * @param height
     * @param columns
     */
    public void setUpConstraints(int width, int height, int columns) {
        int widthColumn1 = 200;
        int widthExtraColumns = 200;
        ColumnConstraints column1Constraints = new ColumnConstraints();
        column1Constraints.setHalignment(HPos.RIGHT);
        column1Constraints.setPrefWidth(widthColumn1); //Column Width
        setColumnConstraint(1, column1Constraints);
        ColumnConstraints column2Constraints = new ColumnConstraints();
        column2Constraints.setHalignment(HPos.LEFT);
        column2Constraints.setPrefWidth((width-widthColumn1)-(widthExtraColumns*(columns-2))); //Column Width
        setColumnConstraint(2, column2Constraints);
        for (int i=3; i<=columns; i++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.LEFT);
            cc.setPrefWidth(widthExtraColumns);
            setColumnConstraint(i, cc);
        }

        for (int i = 1; i <= rows; i++) {
            setRowConstraint(i, new RowConstraints(height / rows));
        }
    }

    /**
     * Sets the column constraints
     * @param columnIndex
     * @param columnConstraint 
     */
    public void setColumnConstraint(int columnIndex, ColumnConstraints columnConstraint) {
        grid.getColumnConstraints().set(columnIndex-1, columnConstraint);
    }
    /**
     * Sets the row constraints
     * @param rowIndex
     * @param rowConstraint 
     */
    public void setRowConstraint(int rowIndex, RowConstraints rowConstraint){
        grid.getRowConstraints().set(rowIndex-1, rowConstraint);
    }
    
    /**
     * Sets the Hgap
     * @param width 
     */
    public void setHgap(int width) {
        grid.setHgap(width);
    }
    
    //</editor-fold>
    
    public int getRows(){return rows;}
    
    /**
     * Disables some nodes
     * @param disable
     * @param keys 
     */
    public void setDisable(boolean disable, String... keys){
        Arrays.stream(keys).forEach(k -> {
            if (nodes.containsKey(k)) nodes.get(k).setDisable(disable); 
        });
    }
    /**
     * Disables all nodes
     * @param disable 
     */
    public void setAllDisabled(boolean disable){
        nodes.keySet().forEach(key -> {
            setDisable(disable, key);
        });
    }
    /**
     * Sets some nodes (in)visible
     * @param visible
     * @param keys 
     */
    public void setVisible(boolean visible, String... keys){
        Arrays.stream(keys).forEach(k -> {
            if (nodes.containsKey(k)) nodes.get(k).setVisible(visible); 
        });
    }
    //</editor-fold>

}
