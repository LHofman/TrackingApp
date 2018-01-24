package domain.entity;

import domain.MyDate;
import domain.enums.Status;
import domain.item.TvShow;
import exceptions.InvalidArgumentException;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@Book}
 */
public class EpisodeTest {
    
    private Episode instance;
    private TvShow tvShow;
    
    /**
     * Inits the instance
     */
    @Before
    public void setUp() {
        tvShow = new TvShow("The Legend of Korra", new MyDate(2012, 4, 14), false, true, Status.ToDo);
        instance = new Episode(1, 1, "Welcome to Republic City", new MyDate(2012, 4, 14), true, true);
        instance.setTvShow(tvShow);
    }
    
    /**
     * Tests the {@link Episode#setSeason(int)} and {@link Episode#getSeason()} methods
     */
    @Test
    public void testSetGetSeason(){
        instance.setSeason(5);
        assertEquals(5, instance.getSeason());
    }
    
    /**
     * Tests the {@link Episode#setSeason(int)} method
     * with a negative parameter
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetSeasonNegative(){
        instance.setSeason(-1);
    }
    
    /**
     * Tests the {@link Episode#setEpisodeNr(int)} and {@link Episode#getEpisodeNr()} methods
     */
    @Test
    public void testSetGetEpisodeNr(){
        instance.setEpisodeNr(5);
        assertEquals(5, instance.getEpisodeNr());
    }
    
    /**
     * Tests the {@link Episode#setEpisodeNr(int)} method
     * with a negative parameter
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetEpisodeNrNegative(){
        instance.setSeason(-1);
    }
    
    /**
     * Tests the {@link Episode#setTitle(java.lang.String)} and {@link Episode#getTitle()} methods
     */
    @Test
    public void testSetGetTitle(){
        instance.setTitle("A Leaf in the Wind");
        assertEquals("A Leaf in the Wind", instance.getTitle());
    }
    
    /**
     * Tests the {@link Episode#setTitle(java.lang.String)} method
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetTitleNull(){
        instance.setTitle(null);
    }
    
    /**
     * Tests the {@link Episode#setReleaseDate(domain.MyDate)} and {@link Episode#getReleaseDate()} methods
     */
    @Test
    public void testSetGetReleaseDate(){
        MyDate releaseDate = new MyDate(2018, 1 ,18);
        instance.setReleaseDate(releaseDate);
        assertEquals(0, instance.getReleaseDate().compareTo(releaseDate));
    }
    
    /**
     * Tests the {@link Episode#setReleaseDate(domain.MyDate)} method
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetReleaseDateNull(){
        instance.setReleaseDate((MyDate)null);
    }
    
    /**
     * Tests the {@link Episode#setReleaseDate(java.util.Calendar)} method
     */
    @Test
    public void testSetReleaseDateCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 1, 18);
        instance.setReleaseDate(cal);
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2018, 1, 18)));
    }
    
    /**
     * Tests the {@link Episode#setReleaseDate(java.util.Calendar)} method
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetReleaseDateCalendarNull(){
        instance.setReleaseDate((Calendar)null);
    }
    
    /**
     * Tests the {@link Episode#setWatched(boolean)} and {@link Episode#isWatched()} methods
     */
    @Test
    public void testSetGetWatched(){
        instance.setWatched(true);
        assertTrue(instance.isWatched());
    }
    
    /**
     * Tests the {@link Episode#setInCollection(boolean)} and {@link Episode#isInCollection()} methods
     */
    @Test
    public void testSetGetInCollection(){
        instance.setInCollection(false);
        assertFalse(instance.isInCollection());
    }
    
    /**
     * Tests the {@link Episode#setTvShow(domain.item.TvShow)} and {@link Episode#getTvShow()} methods
     */
    @Test
    public void testSetGetTvShow(){
        instance.setTvShow(new TvShow("test", new MyDate(2001, 1, 1), true));
        assertEquals("test", instance.getTvShow().getTitle());
    }
    
    /**
     * Tests the {@link Episode#setTvShow(domain.item.TvShow)} method
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetTvShowNull(){
        instance.setTvShow(null);
    }
    
    /**
     * Tests the {@link Episode#init(int, int, java.lang.String, domain.MyDate, boolean, boolean)} method
     */
    @Test
    public void testInit(){
        instance.init(1, 2, "A Leaf in the Wind", new MyDate(2012, 4, 14));
        assertEquals(1, instance.getSeason());
        assertEquals(2, instance.getEpisodeNr());
        assertEquals("A Leaf in the Wind", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2012, 4, 14)));
    }
    
    /**
     * Tests the {@link Episode#editEpisode(domain.entity.Episode)} method
     */
    @Test
    public void testEditEpisode(){
        instance.editEpisode(new Episode(1, 2, "A Leaf in the Wind", new MyDate(2012, 4, 14), true, true));
        assertEquals(1, instance.getSeason());
        assertEquals(2, instance.getEpisodeNr());
        assertEquals("A Leaf in the Wind", instance.getTitle());
        assertEquals(0, instance.getReleaseDate().compareTo(new MyDate(2012, 4, 14)));
        assertEquals(true, instance.isWatched());
        assertEquals(true, instance.isInCollection());
    }
    
    /**
     * Tests the {@link Episode#setSeason(int)} and {@link Episode#getSeason()} methods
     */
    @Test
    public void testToString(){
        assertEquals("The Legend of Korra S1E1: Welcome to Republic City", instance.toString());
    }
    
}
