package domain.item;

import domain.MyDate;
import domain.enums.Status;

/**
 * Helper class to create an instance of an {@link domain.item.Item} subclass
 */
public abstract class ItemFactory {
    
    /**
     * @param iClass The subclass to be created
     * @param title
     * @param releaseDate
     * @return A newly create instance of an Item
     */
    public static Item createItem(Class iClass, String title, MyDate releaseDate){
        return createItem(iClass, title, releaseDate, false, null);
    }
    /**
     * 
     * @param iClass The subclass to be created
     * @param title
     * @param releaseDate
     * @param inCollection
     * @param state
     * @return A newly create instance of an Item
     */
    public static Item createItem(Class iClass, String title, MyDate releaseDate, boolean inCollection, Status state){
        if (iClass == Game.class) return new Game(title, releaseDate, inCollection, state);
        else if (iClass == Movie.class) return new Movie(title, releaseDate, inCollection, state);
        else if (iClass == TvShow.class) return new TvShow(title, releaseDate, inCollection, state);
        return null;
    }
    
}
