package domain.item;

import domain.MyDate;
import domain.entity.Person;
import domain.enums.Status;
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
        assertEquals("Someone", instance.getAuthor().getName());
        assertEquals("Else", instance.getAuthor().getFirstName());
    }
    /**
     * Tests {@link domain.item.Book#setAuthor(domain.Person)}
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetAuthorNull() {
        instance.setAuthor(null);
    }
    
    /**
     * Tests {@link domain.item.Book#editBook(domain.item.Book)}
     */
    @Test
    public void testEditBook(){
        instance.editBook(new Book("title", new MyDate(2017, 12, 25), new Person("author", "author"), true, Status.Done));
        assertEquals("title", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2017, 12, 25)));
        assertEquals("author", instance.getAuthor().getName());
        assertEquals("author", instance.getAuthor().getFirstName());
        assertTrue(instance.isInCollection());
        assertEquals(Status.Done, instance.getStatus());
    }

    /**
     * Tests {@link domain.item.Book#editBook(domain.item.Book)}
     * with parameter null
     */
    @Test
    public void testEditBookNull(){
        instance.editBook(null);
        assertEquals("Origin", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2017, 10, 3)));
        assertEquals("Brown", instance.getAuthor().getName());
        assertEquals("Dan", instance.getAuthor().getFirstName());
        assertFalse(instance.isInCollection());
        assertEquals(Status.ToDo, instance.getStatus());
    }

}
