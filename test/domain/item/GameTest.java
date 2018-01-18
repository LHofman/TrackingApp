package domain.item;

import domain.MyDate;
import domain.entity.GameObjective;
import domain.enums.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@Book}
 */
public class GameTest {
    
    private Game instance;
    private List<GameObjective> objectives;
    
    /**
     * Initiates the instance
     */
    @Before
    public void setUp() {
        instance = new Game("Ratchet and Clank", new MyDate(2002, 11, 4), true, Status.Done, new ArrayList<>());
        objectives = Arrays.asList(new GameObjective[]{new GameObjective(1, "Defeat Drek", true)});
    }
    
    /**
     * Test of {@link Game#setObjectives(java.util.List)} and {@link Game#getObjectives()}
     */
    @Test
    public void testSetGetObjectives() {
        instance.setObjectives(objectives);
        assertEquals(objectives, instance.getObjectives());
    }
    /**
     * Tests {@link Game#setObjectives(java.util.List)}
     * with parameter null
     */
    @Test
    public void testSetObjectivesNull() {
        instance.setObjectives(null);
        assertEquals(new ArrayList<>(), instance.getObjectives());
    }
    
    /**
     * Tests {@link Game#editGame(Game)}
     */
    @Test
    public void testEditGame(){
        instance.editGame(new Game("Lego Batman 2", new MyDate(2012, 6, 19), true, Status.Done, objectives));
        assertEquals("Lego Batman 2", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2012, 6, 19)));
        assertTrue(instance.isInCollection());
        assertEquals(Status.Done, instance.getStatus());
        assertEquals(objectives, instance.getObjectives());
    }

    /**
     * Tests {@link Game#editGame(Game)}
     * with parameter null
     */
    @Test
    public void testEditGameNull(){
        instance.editGame(null);
        assertEquals("Ratchet and Clank", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2002, 11, 4)));
        assertTrue(instance.isInCollection());
        assertEquals(Status.Done, instance.getStatus());
        assertEquals(new ArrayList<>(), instance.getObjectives());
    }
    
    /**
     * Tests {@link Game#addObjectives(domain.entity.GameObjective...) }
     */
    @Test
    public void testAddObjectives(){
        instance.addObjectives(objectives.get(0));
        assertTrue(instance.getObjectives().contains(objectives.get(0)));
    }
    
    /**
     * Tests {@link Game#addObjectives(domain.entity.GameObjective...) }
     * with parameter null
     */
    @Test
    public void testAddObjectivesNull(){
        instance.addObjectives(null);
        assertEquals(0, instance.getObjectives().size());
    }

    /**
     * Tests {@link Game#removeObjectives(domain.entity.GameObjective...) }
     */
    @Test
    public void testRemoveObjectives(){
        instance.setObjectives(objectives);
        instance.removeObjectives(objectives.get(0));
        assertFalse(instance.getObjectives().contains(objectives.get(0)));
    }
    
    /**
     * Tests {@link Game#removeObjectives(domain.entity.GameObjective...) }
     * with parameter null
     */
    @Test
    public void testRemoveObjectivesNull(){
        instance.setObjectives(objectives);
        instance.removeObjectives(null);
        assertEquals(1, instance.getObjectives().size());
    }

}
