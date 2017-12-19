package domain.item;

import domain.MyDate;
import domain.Person;
import exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@Book}
 */
public class BookTest {
    
    private Book instance;
    
    @Before
    public void setUp() {
        instance = new Book("Origin", new MyDate(2017, 10, 3), new Person("Brown", "Dan"));
    }
    
    /**
     * Test of setAuthor and getAuthor methods, of class Book.
     */
    @Test
    public void testSetGetAuthor() {
        Person author = new Person("Someone", "Else");
        instance.setAuthor(author);
        assertEquals(author, instance.getAuthor());
    }
    /**
     * Test of getAuthor and setAuthor methods, of class Book.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testGetAuthorNull() {
        instance.setAuthor(null);
    }

}
