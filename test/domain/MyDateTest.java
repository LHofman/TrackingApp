package domain;

import exceptions.InvalidArgumentException;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@link domain.MyDate}
 */
public class MyDateTest {
    
    MyDate instance;
    
    @Before
    public void setUp() {
        
    }
    
    /**
     * Test of setDate and getDate methods, of class MyDate.
     * With a Calendar as parameter
     */
    @Test
    public void testSetDate_Calendar() {
        Calendar date = Calendar.getInstance();
        instance = new MyDate(date);
        assertEquals(date, instance.getDate());
    }

    /**
     * Test of setDate method, of class MyDate.
     * With a Calendar (null) as parameter
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetDate_CalendarNull() {
        instance = new MyDate(null);
    }

    /**
     * Test of setDate and getDate methods, of class MyDate.
     * With year, month and day as parameters
     */
    @Test
    public void testSetGetDate_Year_Month_Day() {
        int year = 2017, month = 12, day = 19;
        instance = new MyDate(year, month, day);
        assertEquals(year, instance.getYear());
        assertEquals(month, instance.getMonth());
        assertEquals(day, instance.getDay());
    }

    /**
     * Test of setDate method, of class MyDate.
     * With year, month and day as parameters (invalid date)
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetGetDate_Year_Month_Day_Invalid() {
        instance = new MyDate(2017, 2, 29);
    }

}
