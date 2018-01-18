package domain.item;

import domain.MyDate;
import domain.entity.Episode;
import domain.entity.Episode;
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
public class TvShowTest {
    
    private TvShow instance;
    private List<Episode> episodes;
    
    /**
     * Initiates the instance
     */
    @Before
    public void setUp() {
        instance = new TvShow("Scrubs", new MyDate(2001, 10 ,2), true, Status.Done, new ArrayList<>());
        episodes = Arrays.asList(new Episode[]{new Episode(1, 1, "My First Day", new MyDate(2001, 10, 2), true, true)});
    }
    
    /**
     * Test of {@link TvShow#setEpisodes(java.util.List)} and {@link TvShow#getEpisodes()}
     */
    @Test
    public void testSetGetEpisodes() {
        instance.setEpisodes(episodes);
        assertEquals(episodes, instance.getEpisodes());
    }
    
    /**
     * Tests {@link TvShow#setEpisodes(java.util.List)}
     * with parameter null
     */
    @Test
    public void testSetEpisodesNull() {
        instance.setEpisodes(null);
        assertEquals(new ArrayList<>(), instance.getEpisodes());
    }
    
    /**
     * Tests {@link TvShow#getEpisodes(int)} method
     */
    @Test
    public void testGetEpisodesSeason() {
        instance.setEpisodes(episodes);
        assertEquals(episodes, instance.getEpisodes(1));
    }
    
    /**
     * Tests {@link TvShow#getSeasons()} method
     */
    @Test
    public void testGetSeasons() {
        instance.setEpisodes(episodes);
        assertEquals(Arrays.asList(new Integer[]{1}), instance.getSeasons());
    }
    
    /**
     * Tests {@link TvShow#editTvShow(TvShow)}
     */
    @Test
    public void testEditTvShow(){
        instance.editTvShow(new TvShow("Seinfeld", new MyDate(1989, 7, 5), false, Status.Done, episodes));
        assertEquals("Seinfeld", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(1989, 7, 5)));
        assertFalse(instance.isInCollection());
        assertEquals(Status.Done, instance.getStatus());
        assertEquals(episodes, instance.getEpisodes());
    }

    /**
     * Tests {@link TvShow#editTvShow(TvShow)}
     * with parameter null
     */
    @Test
    public void testEditTvShowNull(){
        instance.editTvShow(null);
        assertEquals("Scrubs", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2001, 10, 2)));
        assertTrue(instance.isInCollection());
        assertEquals(Status.Done, instance.getStatus());
        assertEquals(new ArrayList<>(), instance.getEpisodes());
    }
    
    /**
     * Tests {@link TvShow#addEpisodes(domain.entity.Episode...) }
     */
    @Test
    public void testAddEpisodes(){
        instance.addEpisodes(episodes.get(0));
        assertTrue(instance.getEpisodes().contains(episodes.get(0)));
    }
    
    /**
     * Tests {@link TvShow#addEpisodes(domain.entity.Episode...) }
     * with parameter null
     */
    @Test
    public void testAddEpisodesNull(){
        instance.addEpisodes(null);
        assertEquals(0, instance.getEpisodes().size());
    }

    /**
     * Tests {@link TvShow#removeEpisodes(domain.entity.Episode...)} method
     */
    @Test
    public void testRemoveEpisodes(){
        instance.setEpisodes(episodes);
        instance.removeEpisodes(episodes.get(0));
        assertFalse(instance.getEpisodes().contains(episodes.get(0)));
    }
    
    /**
     * Tests {@link TvShow#removeEpisodes(domain.entity.Episode...)} method
     * with parameter null
     */
    @Test
    public void testRemoveEpisodesNull(){
        instance.setEpisodes(episodes);
        instance.removeEpisodes(null);
        assertEquals(1, instance.getEpisodes().size());
    }

    /**
     * Tests {@link TvShow#isWatched(int)} method
     */
    @Test
    public void testIsWatched(){
        instance.setEpisodes(episodes);
        assertTrue(instance.isWatched(1));
    }
    
    /**
     * Tests {@link TvShow#isCollected()} method
     */
    @Test
    public void testIsInCollection(){
        instance.setEpisodes(episodes);
        assertTrue(instance.isCollected(1));
    }
    
}
