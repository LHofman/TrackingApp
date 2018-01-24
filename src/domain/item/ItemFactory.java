package domain.item;

import domain.MyDate;
import domain.enums.ItemType;
import domain.enums.Status;

/**
 * Helper class to create an instance of an {@link domain.item.Item} subclass
 */
public abstract class ItemFactory {
    
    /**
     * 
     * @param type
     * @param title
     * @param releaseDate
     * @return A newly create instance of an Item
     */
    public static Item createItem(ItemType type, String title, MyDate releaseDate){
        switch (type){
            case Game: return new Game(title, releaseDate);
            case Movie: return new Movie(title, releaseDate);
            default: return null;
        }
    }
    
    /**
     * 
     * @param type
     * @param title
     * @param releaseDate
     * @param inCollection
     * @param status
     * @return A newly create instance of an Item
     */
    public static Item createItem(ItemType type, String title, MyDate releaseDate, boolean inCollection, Status status){
        switch (type){
            case Game: return new Game(title, releaseDate, inCollection, status);
            case Movie: return new Movie(title, releaseDate, inCollection, status);
            default: return null;
        }
    }
    
}
