package domain.entity;

import domain.MyDate;
import domain.entity.Person;
import domain.item.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Test class for {@link Person}
 */
public class PersonTest {
    
    private Person instance;

    @Before
    public void setUp(){
        instance = new Person("Test","Test");
    }
    
    /**
     * Test of person constructor
     */
    @Test
    public void testConstructor() {
        instance = new Person("Hofman", "Lennert");
        assertEquals(new ArrayList<>(), instance.getBooks());
        assertEquals("Hofman", instance.getName());
        assertEquals("Lennert", instance.getFirstName());
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    /**
     * Tests {@link domain.Person#setName(java.lang.String)} and {@link domain.Person#getName()}
     */
    @Test
    public void testSetGetName(){
        instance.setName("The Name");
        assertEquals("The Name", instance.getName());
    }
    
    /**
     * Tests {@link domain.Person#setFirstName(java.lang.String)} and {@link domain.Person#getFirstsName()}
     */
    @Test
    public void testSetGetFirstName(){
        instance.setFirstName("The First Name");
        assertEquals("The First Name", instance.getFirstName());
    }
    
    /**
     * Tests {@link domain.Person#setBooks(java.util.List)} and {@link domain.Person#getBooks()}
     */
    @Test
    public void testSetGetBooks(){
        List<Book> books = Arrays.asList(new Book[]{new Book("title", new MyDate(2017, 12, 25), new Person("author", "author"))});
        instance.setBooks(books);
        assertEquals(books, instance.getBooks());
    }
    
    /**
     * Tests {@link domain.Person#setBooks(java.util.List)} and {@link domain.Person#getBooks()}
     * whith parameter null
     */
    @Test
    public void testSetBooksNull(){
        instance.setBooks(null);
        assertEquals(new ArrayList<>(), instance.getBooks());
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="editPerson">
    
    /**
     * Tests {@link domain.Person#editPerson(domain.Person)}
     */
    @Test
    public void testEditPerson(){
        instance.editPerson(new Person("myName", "myFName"));
        assertEquals("myName", instance.getName());
        assertEquals("myFName", instance.getFirstName());
    }
    
    /**
     * Tests {@link domain.Person#editPerson(domain.Person)}
     * with parameter null
     */
    @Test
    public void testEditPersonNull(){
        instance.editPerson(null);
        assertEquals("Test", instance.getName());
        assertEquals("Test", instance.getFirstName());
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="add/remove books">
    
    /**
     * Tests {@link domain.Person#addBooks(domain.item.Book...)} and {@link domain.Person#removeBooks(domain.item.Book...)}
     */
    @Test
    public void testAddRemoveBooks(){
        Book book = new Book("title", new MyDate(2017, 12, 25), new Person("author", "author"));
        assertFalse(instance.getBooks().contains(book));
        instance.addBooks(book);
        assertTrue(instance.getBooks().contains(book));
        instance.removeBooks(book);
        assertFalse(instance.getBooks().contains(book));
    }
    
    /**
     * Tests {@link domain.Person#addBooks(domain.item.Book...)}
     * with parameter null
     */
    @Test
    public void testAddBooksNull(){
        int size = instance.getBooks().size();
        instance.addBooks(null);
        assertEquals(size, instance.getBooks().size());
    }
    
    /**
     * Tests {@link domain.Person#removeBooks(domain.item.Book...)}
     */
    @Test
    public void testRemoveBooksNull(){
        instance.removeBooks(null);
        //did not throw an exception
        assertTrue(true);
    }
    
    //</editor-fold>
    
    /**
     * Tests {@link domain.Person#toString()}
     */
    @Test
    public void testToString(){
        instance = new Person("theName", "fName");
        assertEquals("fName theName", instance.toString());
    }
    
    //</editor-fold>
    
}
