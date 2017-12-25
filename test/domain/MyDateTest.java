package domain;

import exceptions.InvalidArgumentException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for {@link domain.MyDate}
 */
public class MyDateTest {
    
    MyDate instance;
    Calendar date;
    
    @Before
    public void setUp() {
        date = Calendar.getInstance();
        instance = new MyDate(date);
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    //<editor-fold defaultstate="collapsed" desc="setCalendar">
    
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
        Calendar cal = null;
        instance = new MyDate(cal);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setYearMonthDay">
    
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

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setLocalDate">
    
    /**
     * Test of {@link domain.MyDate#setDate(java.time.LocalDate)} and {@link domain.MyDate#getDateAsLocalDate()} methods
     */
    @Test
    public void testSetDate_LocalDate() {
        LocalDate date = Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        instance = new MyDate(date);
        assertEquals(date, instance.getDateAsLocalDate());
    }

    /**
     * Test of {@link domain.MyDate#setDate(java.time.LocalDate)} and {@link domain.MyDate#getDateAsLocalDate()} methods
     * With parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetDate_LocalDateNull() {
        LocalDate date = null;
        instance = new MyDate(date);
    }

    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="setDate">
    
    /**
     * Test of {@link domain.MyDate#setDate(java.time.Date)} and {@link domain.MyDate#getDateAsDate()} methods
     */
    @Test
    public void testSetDate_Date() {
        Date date = Calendar.getInstance().getTime();
        instance = new MyDate(date);
        assertEquals(date, instance.getDateAsDate());
    }

    /**
     * Test of {@link domain.MyDate#setDate(java.time.Date)} and {@link domain.MyDate#getDateAsDate()} methods
     * With parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testSetDate_DateNull() {
        Date date = null;
        instance = new MyDate(date);
    }

    //</editor-fold>
   
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="comparing methods">
    
    //<editor-fold defaultstate="collapsed" desc="compareTo">
    
    /**
     * Test of {@link domain.MyDate#compareTo(domain.MyDate)}
     */
    @Test
    public void testCompareTo(){
        Calendar dateBefore = Calendar.getInstance(), dateAfter = Calendar.getInstance();
        dateBefore.add(Calendar.DATE, -1);
        dateAfter.add(Calendar.DATE, 1);
        
        assertEquals(0, instance.compareTo(instance));
        assertTrue(instance.compareTo(new MyDate(dateBefore))>0);
        assertTrue(instance.compareTo(new MyDate(dateAfter))<0);
    }
    
    /**
     * Test of {@link domain.MyDate#compareTo(domain.MyDate)}
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCompareToNull(){
        instance.compareTo(null);
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="before">
    
    /**
     * Test of {@link domain.MyDate#before(domain.MyDate)}
     */
    @Test
    public void testBefore(){
        Calendar dateBefore = Calendar.getInstance(), dateAfter = Calendar.getInstance();
        dateBefore.add(Calendar.DATE, -1);
        dateAfter.add(Calendar.DATE, 1);
        
        assertFalse(instance.before(instance));
        assertTrue(instance.before(new MyDate(dateAfter)));
        assertFalse(instance.before(new MyDate(dateBefore)));
    }
    
    /**
     * Test of {@link domain.MyDate#before(domain.MyDate)}
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testBeforeNull(){
        instance.before(null);
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="after">
    
    /**
     * Test of {@link domain.MyDate#after(domain.MyDate)}
     */
    @Test
    public void testAfter(){
        Calendar dateBefore = Calendar.getInstance(), dateAfter = Calendar.getInstance();
        dateBefore.add(Calendar.DATE, -1);
        dateAfter.add(Calendar.DATE, 1);
        
        assertFalse(instance.after(instance));
        assertFalse(instance.after(new MyDate(dateAfter)));
        assertTrue(instance.after(new MyDate(dateBefore)));
    }
    
    /**
     * Test of {@link domain.MyDate#after(domain.MyDate)}
     * with parameter null
     */
    @Test(expected = InvalidArgumentException.class)
    public void testAfterNull(){
        instance.after(null);
    }
    
    //</editor-fold>

    //</editor-fold>

    /**
     * Test of {@link domain.MyDate#toString()}
     */
    @Test
    public void testToString(){
        assertEquals("24-12-2017", new MyDate(2017, 12, 24).toString());
    }
    
    /**
     * Test of {@link domain.MyDate#equals(java.lang.Object)}
     */
    @Test
    public void testEquals(){
        MyDate instance2 = new MyDate(Calendar.getInstance());
        assertTrue(instance.equals(instance));
        assertTrue(instance.equals(instance2));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(new MyDate(2017, 12, 23)));
    }
    
}
