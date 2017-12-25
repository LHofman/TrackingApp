package domain.item;

import domain.*;
import domain.enums.ItemType;
import domain.enums.Status;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Test class for {@link Item}
 */
public class ItemTest {
    
    private Item instance;
    
    @Before
    public void setUp(){
        instance = new ItemImpl("title", new MyDate(2017, 12, 25), false, Status.Done);
    }

    //<editor-fold defaultstate="collapsed" desc="constructors">
    
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

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="editItem">
    
    /**
     * Tests {@link domain.item.Item#editItem(domain.item.Item)}
     */
    @Test
    public void testEditItem(){
        instance.editItem(new ItemImpl("title2", new MyDate(2017, 12, 24), true, Status.Doing));
        assertEquals("title2", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2017, 12, 24)));
        assertTrue(instance.isInCollection());
        assertEquals(Status.Doing, instance.getStatus());
    }
    
    /**
     * Tests {@link domain.item.Item#editItem(domain.item.Item)}
     * with parameter null
     */
    @Test
    public void testEditItemNull(){
        instance.editItem(null);
        assertEquals("title", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2017, 12, 25)));
        assertFalse(instance.isInCollection());
        assertEquals(Status.ToDo, instance.getStatus());
    }
    
    /**
     * Tests {@link domain.item.Item#editItem(domain.item.Item)}
     * where the parameter is a wrong type
     */
    @Test
    public void testEditItemWrongType(){
        instance.editItem(new Movie("title2", new MyDate(2017, 12, 24), true, Status.Doing));
        assertEquals("title", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2017, 12, 25)));
        assertFalse(instance.isInCollection());
        assertEquals(Status.ToDo, instance.getStatus());
    }
    
    //</editor-fold>

    private class ItemImpl extends Item{
        public ItemImpl(String title, MyDate releaseDate){super(title, releaseDate);}
        public ItemImpl(String title, MyDate releaseDate, boolean inCollection, Status state){
            super(title, releaseDate, inCollection, state);
        }
        @Override
        public ItemType getType() {
            return ItemType.Movie;
        }
    }

}
