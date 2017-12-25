package domain.item;

import domain.MyDate;
import domain.enums.ItemType;
import domain.enums.Status;

/**
 * Helper class to create an instance of an {@link domain.item.Item} subclass
 */
public abstract class ItemFactory {
    
    /**
     * @param type
     * @param title
     * @param releaseDate
     * @return A newly create instance of an Item
     */
    public static Item createItem(ItemType type, String title, MyDate releaseDate){
        return createItem(type, title, releaseDate, false, null);
    }
    /**
     * 
     * @param type
     * @param title
     * @param releaseDate
     * @param inCollection
     * @param state
     * @return A newly create instance of an Item
     */
    public static Item createItem(ItemType type, String title, MyDate releaseDate, boolean inCollection, Status state){
        switch (type){
            case Game: return new Game(title, releaseDate, inCollection, state);
            case Movie: return new Movie(title, releaseDate, inCollection, state);
            case TvShow: return new TvShow(title, releaseDate, inCollection, state);
            default: return null;
        }
    }
    
}
