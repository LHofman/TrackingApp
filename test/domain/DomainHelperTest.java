package domain;

import domain.entity.Person;
import domain.entity.IEntity;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.item.Book;
import domain.item.Movie;
import exceptions.InvalidArgumentException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class DomainHelperTest {

    //<editor-fold defaultstate="collapsed" desc="check for valid argument">
    
    //<editor-fold defaultstate="collapsed" desc="checkForValue">
    
    /**
     * Test of checkForValue method, of class DomainHelper.
     */
    @Test
    public void testCheckForValue() {
        DomainHelper.checkForValue("test", "");
        DomainHelper.checkForValue("test", 0);
        DomainHelper.checkForValue("test", false);
        //Method has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForValueNull() {
        DomainHelper.checkForValue("test", null);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkForStringValue">
    
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test
    public void testCheckForStringValue() {
        DomainHelper.checkForStringValue("test", "test");
        //Method has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStringValueNull() {
        DomainHelper.checkForStringValue("test", null);
        //Method has not thrown an exception
    }
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStringValueEmpty() {
        DomainHelper.checkForStringValue("test", "");
        //Method has not thrown an exception
    }
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStringValueSpaces() {
        DomainHelper.checkForStringValue("test", "   ");
        //Method has not thrown an exception
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkForDate">
    
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test
    public void testCheckForDate() {
        DomainHelper.checkForDate(2017, 12, 19);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateLowYear() {
        DomainHelper.checkForDate(0, 12, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateHighYear() {
        DomainHelper.checkForDate(3001, 12, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateLowMonth() {
        DomainHelper.checkForDate(2017, 0, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateHighMonth() {
        DomainHelper.checkForDate(3001, 13, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateLowDay() {
        DomainHelper.checkForDate(2017, 12, 0);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateHighDay() {
        DomainHelper.checkForDate(2017, 2, 29);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkRange">
    
    //<editor-fold defaultstate="collapsed" desc="checkMin">
    
    /**
     * Test of checkMin method, of class DomainHelper.
     */
    @Test
    public void testCheckMin() {
        DomainHelper.checkMin("test", 1, 1);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkMin method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckMinLower() {
        DomainHelper.checkMin("test", 0, 1);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkMax">
    
    /**
     * Test of checkMax method, of class DomainHelper.
     */
    @Test
    public void testCheckMax() {
        DomainHelper.checkMax("test", 1, 1);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkMax method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckMaxHigh() {
        DomainHelper.checkMax("test", 1, 0);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkRange">
    
    /**
     * Test of checkRange method, of class DomainHelper.
     */
    @Test
    public void testCheckRange() {
        DomainHelper.checkRange("test", 1, 1, 1);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkRange method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckRangeLow() {
        DomainHelper.checkRange("test", 0, 1, 1);
    }
    /**
     * Test of checkRange method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckRangeHigh() {
        DomainHelper.checkRange("test", 2, 1, 1);
    }

    //</editor-fold>
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkForStateValue">
    
    /**
     * Test of checkForStateValue method, of class DomainHelper.
     */
    @Test
    public void testCheckForStateValue() {
        Status[] availableStates = {Status.ToDo, Status.Done};
        for (Status state: availableStates)
            DomainHelper.checkForStateValue("test", state, availableStates);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForStateValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStateValueNull() {
        DomainHelper.checkForStateValue("test", null, new Status[0]);
    }
    /**
     * Test of checkForStateValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStateValueUnavailable() {
        DomainHelper.checkForStateValue("test", Status.ToDo, new Status[0]);
    }
    
    //</editor-fold>
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Convert List">
    
    /**
     * Test of {@link domain.DomainHelper#convertList(java.util.List)} method
     */
    @Test
    public void testConvertList() {
        List<IEntity> originalList = Arrays.asList(new IEntity[]{
            new Book("title", new MyDate(2017,12, 24), new Person("name", "firstname")),
            new Book("title2", new MyDate(2017,12, 25), new Person("name2", "firstname2"), true, Status.Done),
            new Book("title3", new MyDate(2017,12, 26), new Person("name3", "firstname3"))
        });
        
        List<Book> convertedList = DomainHelper.convertList(originalList, Book.class);
        
        for (int i=0; i<convertedList.size(); i++)
            assertTrue(convertedList.get(i).equals(originalList.get(i)));
    }
    
    /**
     * Test of {@link domain.DomainHelper#convertList(java.util.List)} method
     * where parameter is null
     */
    @Test
    public void testConvertListNull() {
        List<Book> convertedList = DomainHelper.convertList(null, Book.class);
        
        assertEquals(0, convertedList.size());
    }
    
    /**
     * Test of {@link domain.DomainHelper#convertList(java.util.List)} method
     * where the conversion fails
     */
    @Test
    public void testConvertListFail() {
        List<IEntity> originalList = Arrays.asList(new IEntity[]{
            new Book("title", new MyDate(2017,12, 24), new Person("name", "firstname")),
            new Book("title2", new MyDate(2017,12, 25), new Person("name2", "firstname2"), true, Status.Done),
            new Movie("title3", new MyDate(2017,12, 26))
        });
        
        List<Book> convertedList = DomainHelper.convertList(originalList, Book.class);
        
        assertEquals(2, convertedList.size());
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getAvailableStatuses">
    
    /**
     * Test of {@link domain.DomainHelper#getAvailableStatuses(domain.enums.ItemType)} method
     */
    @Test
    public void testGetAvailableStatuses(){
        Assert.assertArrayEquals(new Book().getAvailableStatuses(), DomainHelper.getAvailableStatuses(ItemType.Book));
    }
    
    /**
     * Test of {@link domain.DomainHelper#getAvailableStatuses(domain.enums.ItemType)} method
     * with parameters null
     */
    @Test
    public void testGetAvailableStatusesNull(){
        Assert.assertArrayEquals(null, DomainHelper.getAvailableStatuses(null));
    }
    
    //</editor-fold>
    
}
