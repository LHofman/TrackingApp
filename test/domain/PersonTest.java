package domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@link Person}
 */
public class PersonTest {
    
    private Person instance;

    /**
     * Test of person constructor
     */
    @Test
    public void testConstructor() {
        instance = new Person("Hofman", "Lennert");
        assertEquals("Hofman", instance.getName());
        assertEquals("Lennert", instance.getFirstName());
    }
}
