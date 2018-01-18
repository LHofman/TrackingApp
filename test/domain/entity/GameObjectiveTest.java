package domain.entity;

import domain.MyDate;
import domain.entity.GameObjective;
import domain.enums.Status;
import domain.item.Game;
import exceptions.InvalidArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@Book}
 */
public class GameObjectiveTest {
    
    private GameObjective instance;
    private Game game;
    private List<GameObjective> objectives;
    private Map<String, String> links;
    
    /**
     * Inits the instance
     */
    @Before
    public void setUp() {
        game = new Game("Super Mario Galaxy", new MyDate(2007, 11, 1), true, Status.Done, new ArrayList<>());
        instance = new GameObjective(2, "Get all 120 Stars", true);
        objectives = Arrays.asList(new GameObjective[]{new GameObjective(1, "Get all Stars from the Gate Galaxies", true)});
        links = new HashMap<String, String>(){{put("IGN Guide", "http://www.ign.com/wikis/super-mario-galaxy");}};
    }
    
    /**
     * Tests the {@link GameObjective#setIndex(int)} and {@link GameObjective#getIndex()} methods
     */
    @Test
    public void testSetGetIndex(){
        instance.setIndex(-1);
        assertEquals(-1, instance.getIndex());
    }
    
    /**
     * Tests the {@link GameObjective#setObjective(java.lang.String)} and {@link GameObjective#getObjective()} methods
     */
    @Test
    public void testSetGetObjective(){
        instance.setObjective("Defeat Bowser");
        assertEquals("Defeat Bowser", instance.getObjective());
    }
    
    /**
     * Tests the {@link GameObjective#setObjective(java.lang.String)} method
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetObjectiveNull(){
        instance.setObjective(null);
    }
    
    /**
     * Tests the {@link GameObjective#setCompleted(boolean)} and {@link GameObjective#isCompleted()} methods
     */
    @Test
    public void testSetGetCompleted(){
        instance.setCompleted(false);
        assertFalse(instance.isCompleted());
    }
    
    /**
     * Tests the {@link GameObjective#setLinks(java.util.Map)} and {@link GameObjective#getLinks()} methods
     */
    @Test
    public void testSetGetLinks(){
        instance.setLinks(links);
        assertEquals(links, instance.getLinks());
    }
    
    /**
     * Tests the {@link GameObjective#setLinks(java.util.Map)} method
     * with parameter null
     */
    @Test
    public void testSetLinksNull(){
        instance.setLinks(null);
        assertEquals(new HashMap<>(), instance.getLinks());
    }
    
    /**
     * Test of {@link GameObjective#setObjectives(java.util.List)} and {@link GameObjective#getObjectives()}
     */
    @Test
    public void testSetGetObjectives() {
        instance.setObjectives(objectives);
        assertEquals(objectives, instance.getObjectives());
    }
    /**
     * Tests {@link GameObjective#setObjectives(java.util.List)}
     * with parameter null
     */
    @Test
    public void testSetObjectivesNull() {
        instance.setObjectives(null);
        assertEquals(new ArrayList<>(), instance.getObjectives());
    }
    
    /**
     * Tests {@link GameObjective#editGameObjective(domain.entity.GameObjective)}
     */
    @Test
    public void testEditGameObjevice(){
        instance.editGameObjective(new GameObjective(5, "Defeat Bowser", true));
        assertEquals(5, instance.getIndex());
        assertEquals("Defeat Bowser", instance.getObjective());
        assertTrue(instance.isCompleted());
    }

    /**
     * Tests {@link GameObjective#editGameObjective(domain.entity.GameObjective)}
     * with parameter null
     */
    @Test
    public void testEditGameNull(){
        instance.editGameObjective(null);
        assertEquals("Get all 120 Stars", instance.getObjective());
        assertEquals(2, instance.getIndex());
        assertTrue(instance.isCompleted());
    }
    
    /**
     * Tests the {@link GameObjective#addLink(java.lang.String, java.lang.String)} method
     */
    @Test
    public void testAddLinks(){
        instance.addLink("IGN Guide", "http://www.ign.com/wikis/super-mario-galaxy");
        assertTrue(instance.getLinks().containsKey("IGN Guide"));
    }
    
    /**
     * Tests the {@link GameObjective#addLink(java.lang.String, java.lang.String)} method
     * with parameter name = null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testAddLinksNameNull(){
        instance.addLink(null, "http://www.ign.com/wikis/super-mario-galaxy");
        assertEquals(0, instance.getLinks().size());
    }
    
    /**
     * Tests the {@link GameObjective#addLink(java.lang.String, java.lang.String)} method
     * with parameter link = null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testAddLinksLinkNull(){
        instance.addLink("IGN Guide", null);
        assertEquals(0, instance.getLinks().size());
    }
    
    /**
     * Tests the {@link GameObjective#removeLinks(java.lang.String...)} method
     */
    @Test
    public void testRemoveLinks(){
        instance.setLinks(links);
        instance.removeLinks("IGN Guide");
        assertFalse(instance.getLinks().containsKey("IGN Guide"));
    }
    
    /**
     * Tests the {@link GameObjective#removeLinks(java.lang.String...)} method
     * with parameter null
     */
    @Test
    public void testRemoveLinksNull(){
        instance.setLinks(links);
        instance.removeLinks(null);
        assertEquals(1, instance.getLinks().size());
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
    
    /**
     * Tests the {@link GameObjective#hasObjectives()} method
     */
    @Test
    public void testHasObjectives(){
        instance.setObjectives(null);
        assertFalse(instance.hasObjectives());
        instance.setObjectives(objectives);
        assertTrue(instance.hasObjectives());
    }

}
