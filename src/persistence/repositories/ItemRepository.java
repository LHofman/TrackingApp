package persistence.repositories;

import domain.item.Item;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import persistence.PersistenceAdministrator;

/**
 * Superclass for all repositories of {@link domain.item.Item}
 * Sorts items based on title and release date
 * @param <E> 
 */
public class ItemRepository<E extends Item> extends Repository<E>{
    
    public ItemRepository(Class<E> iClass, PersistenceAdministrator pa) {
        super(iClass, pa);
        itemList = (List<E>)itemList.stream()
                .sorted(Comparator.comparing(E::getTitle).thenComparing(E::getReleaseDate))
                .collect(Collectors.toList());
        observableItemList = FXCollections.observableArrayList(itemList);
    }
    
}
