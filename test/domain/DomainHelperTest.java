package domain;

import domain.enums.Status;
import exceptions.InvalidArgumentException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DomainHelperTest {

    //<editor-fold defaultstate="collapsed" desc="checkForValue">
    
    /**
     * Test of checkForValue method, of class DomainHelper.
     */
    @Test
    public void testCheckForValue() {
        DomainHelper.checkForValue("test", "");
        DomainHelper.checkForValue("test", 0);
        DomainHelper.checkForValue("test", false);
        //Method has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForValueNull() {
        DomainHelper.checkForValue("test", null);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkForStringValue">
    
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test
    public void testCheckForStringValue() {
        DomainHelper.checkForStringValue("test", "test");
        //Method has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStringValueNull() {
        DomainHelper.checkForStringValue("test", null);
        //Method has not thrown an exception
    }
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStringValueEmpty() {
        DomainHelper.checkForStringValue("test", "");
        //Method has not thrown an exception
    }
    /**
     * Test of checkForStringValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStringValueSpaces() {
        DomainHelper.checkForStringValue("test", "   ");
        //Method has not thrown an exception
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkForDate">
    
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test
    public void testCheckForDate() {
        DomainHelper.checkForDate(2017, 12, 19);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateLowYear() {
        DomainHelper.checkForDate(0, 12, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateHighYear() {
        DomainHelper.checkForDate(3001, 12, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateLowMonth() {
        DomainHelper.checkForDate(2017, 0, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateHighMonth() {
        DomainHelper.checkForDate(3001, 13, 19);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateLowDay() {
        DomainHelper.checkForDate(2017, 12, 0);
    }
    /**
     * Test of checkForDate method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForDateHighDay() {
        DomainHelper.checkForDate(2017, 2, 29);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkRange">
    
    //<editor-fold defaultstate="collapsed" desc="checkMin">
    
    /**
     * Test of checkMin method, of class DomainHelper.
     */
    @Test
    public void testCheckMin() {
        DomainHelper.checkMin("test", 1, 1);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkMin method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckMinLower() {
        DomainHelper.checkMin("test", 0, 1);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkMax">
    
    /**
     * Test of checkMax method, of class DomainHelper.
     */
    @Test
    public void testCheckMax() {
        DomainHelper.checkMax("test", 1, 1);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkMax method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckMaxHigh() {
        DomainHelper.checkMax("test", 1, 0);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkRange">
    
    /**
     * Test of checkRange method, of class DomainHelper.
     */
    @Test
    public void testCheckRange() {
        DomainHelper.checkRange("test", 1, 1, 1);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkRange method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckRangeLow() {
        DomainHelper.checkRange("test", 0, 1, 1);
    }
    /**
     * Test of checkRange method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckRangeHigh() {
        DomainHelper.checkRange("test", 2, 1, 1);
    }

    //</editor-fold>
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkForStateValue">
    
    /**
     * Test of checkForStateValue method, of class DomainHelper.
     */
    @Test
    public void testCheckForStateValue() {
        Status[] availableStates = {Status.ToDo, Status.Done};
        for (Status state: availableStates)
            DomainHelper.checkForStateValue("test", state, availableStates);
        //test has not thrown an exception
        assertTrue(true);
    }
    /**
     * Test of checkForStateValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStateValueNull() {
        DomainHelper.checkForStateValue("test", null, new Status[0]);
    }
    /**
     * Test of checkForStateValue method, of class DomainHelper.
     */
    @Test(expected = InvalidArgumentException.class)
    public void testCheckForStateValueUnavailable() {
        DomainHelper.checkForStateValue("test", Status.ToDo, new Status[0]);
    }
    
    //</editor-fold>
    
}
