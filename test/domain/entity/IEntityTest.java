package domain.entity;

import domain.entity.IEntity;
import exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@link domain.IEntity}
 */
public class IEntityTest {

    private IEntity instance;
    
    @Before
    public void setUp() {
        instance = new IEntityImpl();
    }

    /**
     * Test of setId and getId methods, of class IEntity.
     */
    @Test
    public void testSetGetId() {
        assertEquals(instance.getId(), 0);
        instance.setId(1);
        assertEquals(instance.getId(), 1);
    }

    /**
     * Test of setId method with negative id, of class IEntity.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testGetId() {
        instance.setId(-1);
    }

    /**
     * Test of hashCode method, of class IEntity.
     */
    @Test
    public void testHashCode() {
        instance.setId(1);
        assertNotEquals(1, instance.hashCode());
    }

    /**
     * Test of equals method, of class IEntity.
     */
    @Test
    public void testEquals() {
        instance.setId(1);
        assertTrue(instance.equals(instance));
        IEntity instance2 = new IEntityImpl();
        instance2.setId(2);
        assertFalse(instance.equals(instance2));
        instance2.setId(1);
        assertTrue(instance.equals(instance2));
    }

    public class IEntityImpl extends IEntity {
    }
    
}
