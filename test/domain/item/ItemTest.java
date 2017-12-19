package domain.item;

import domain.*;
import domain.enums.Status;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@link Item}
 */
public class ItemTest {
    
    private Item instance;

    /**
     * Test of item constructor with 2 arguments
     */
    @Test
    public void testConstructor_2args() {
        instance = new ItemImpl("test", new MyDate(2017, 12, 19));
        assertEquals("test", instance.getTitle());
        assertEquals(new MyDate(2017, 12, 19), instance.getReleaseDate());
    }
    /**
     * Test of item constructor with 2 arguments
     */
    @Test
    public void testConstructor_4args() {
        instance = new ItemImpl("test", new MyDate(2017, 12, 19), true, Status.ToDo);
        assertEquals("test", instance.getTitle());
        assertEquals(new MyDate(2017, 12, 19), instance.getReleaseDate());
        assertEquals(true, instance.isInCollection());
        assertEquals(Status.ToDo, instance.getStatus());
    }

    
    private class ItemImpl extends Item{
        public ItemImpl(String title, MyDate releaseDate){super(title, releaseDate);}
        public ItemImpl(String title, MyDate releaseDate, boolean inCollection, Status state){
            super(title, releaseDate, inCollection, state);
        }
        @Override
        protected Status[] getAvailableStates() {
            return new Status[]{Status.ToDo};
        }
    }

}
