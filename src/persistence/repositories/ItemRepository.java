package persistence.repositories;

import domain.item.Item;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import persistence.PersistenceAdministrator;

/**
 * Superclass for all repositories of {@link domain.item.Item}
 * Sorts items based on title and release date
 * @param <E> 
 */
public class ItemRepository<E extends Item> extends Repository{
    
    public ItemRepository(Class iClass, PersistenceAdministrator pa) {
        super(iClass, pa);
        itemList = (List<Item>)itemList.stream()
                .sorted(Comparator.comparing(Item::getTitle).thenComparing(Item::getReleaseDate))
                .collect(Collectors.toList());
    }
    
}
