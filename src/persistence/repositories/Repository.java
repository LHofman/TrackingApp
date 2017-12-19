package persistence.repositories;

import domain.IEntity;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceAdministrator;

/**
 * Superclass for all repositories
 * @param <E> 
 */
public class Repository<E extends IEntity> {
    
    protected PersistenceAdministrator pa;
    protected List<E> itemList;
    protected ObservableList<E> observableItemList;
    
    public Repository(Class iClass, PersistenceAdministrator pa){
        this.pa = pa;
        itemList = (List<E>) pa.getAll(iClass);
        observableItemList = FXCollections.observableArrayList(itemList);
    }
    
    public ObservableList<E> getAll(){
        return observableItemList;
    }

    public void add(E... items) {
        pa.persist(items);
        addItemsToList(items);
    }

    public void delete(E... items) {
        pa.remove(items);
        RemoveItemsFromList(items);
    }
    
    public void update(E... items) {
        pa.merge(items);
        RemoveItemsFromList(items);
        addItemsToList(items);
    }
    
    private void addItemsToList(E... items){
        itemList.addAll(Arrays.asList(items));
        observableItemList.addAll(items);
    }
    private void RemoveItemsFromList(E... items){
        itemList.removeAll(Arrays.asList(items));
        observableItemList.removeAll(items);
    }

    
}
