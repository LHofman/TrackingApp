package gui;

import domain.DomainController;
import domain.item.Item;
import javafx.collections.transformation.SortedList;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class for the filter popup
 */
public final class FilterWindowController extends GridPane{

    private final DomainController controller;
    private final CustomFilterGrid grid;
    private final SortedList<Item> items;
    
    public FilterWindowController(SortedList<Item> items){
        this.controller = DomainController.getInstance();
        this.items = items;
        
        grid = new CustomFilterGrid(this);
        grid.addFilters("Type", "Author", "Title", "ReleaseDateStart", "ReleaseDateEnd", "InCollection", "Status");
        grid.setUpConstraints(600, 600, 3);
    }
    
    /**
     * Clears the filters
     */
    public void clearFilters(){
        grid.clearFilters();
    }

}
