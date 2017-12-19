package exceptions;

import org.junit.Test;
import static org.junit.Assert.*;

public class InvalidArgumentExceptionTest {
    
    InvalidArgumentException instance;
    
    /**
     * Test of getMessage method, of class InvalidArgumentException.
     */
    @Test
    public void testGetMessage() {
        instance = new InvalidArgumentException("test");
        assertEquals("invalid test", instance.getMessage());
        instance = new InvalidArgumentException("test", "test");
        assertEquals("invalid test (test)", instance.getMessage());
        instance = new InvalidArgumentException("test", 5, "at least 7");
        assertEquals("invalid test (5), should be at least 7", instance.getMessage());
    }
    
}
