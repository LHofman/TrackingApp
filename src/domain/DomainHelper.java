package domain;

import domain.enums.State;
import exceptions.InvalidArgumentException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class DomainHelper {
    
    //<editor-fold defaultstate="collapsed" desc="check for valid argument">
    
    /**
     * Checks if the value is null
     * @param argument the name of the argument
     * @param value
     */
    public static void checkForValue(String argument, Object value){
        if (value==null)
            throw new InvalidArgumentException(argument);
    }
    
    /**
     * Checks if the value is null or empty
     * @param argument the name of the argument
     * @param value
     */
    public static void checkForStringValue(String argument, String value){
        checkForValue(argument, value);
        if (value.trim().isEmpty())
            throw new InvalidArgumentException(argument);
    }
    
    //<editor-fold defaultstate="collapsed" desc="check for range">
        
    /**
     * Checks if the value is higher or equal to the specified parameter
     * @param argument the name of the argument
     * @param value
     * @param min 
     */
    public static void checkMin(String argument, int value, int min){
        if (value<min)
            throw new InvalidArgumentException(argument, value, "at least " + min);
    }
    
    /**
     * Checks if the value is lower or equal to the specified parameter
     * @param argument the name of the argument
     * @param value
     * @param max
     */
    public static void checkMax(String argument, int value, int max){
        if (value>max)
            throw new InvalidArgumentException(argument, value, "at most " + max);
    }
    
    /**
     * Checks if the value is in range
     * @param argument the name of the argument
     * @param value
     * @param min
     * @param max
     */
    public static void checkRange(String argument, int value, int min, int max){
        checkMin(argument, value, min);
        checkMax(argument, value, max);
    }
    
    //</editor-fold>
    
    /**
     * Checks if the values form a valid date
     * @param year
     * @param month
     * @param day 
     */
    static void checkForDate(int year, int month, int day) {
        checkRange("year", year, 1, 3000);
        checkRange("month", month, 1, 12);
        checkRange("day", day, 1, new GregorianCalendar(year, month-1, 1).getActualMaximum(Calendar.DAY_OF_MONTH));
    }
    
    /**
     * Checks if the value is a valid state
     * @param argument the name of the argument
     * @param state
     * @param availableStates 
     */
    public static void checkForStateValue(String argument, State state, State[] availableStates) {
        checkForValue(argument, state);
        if (!Arrays.asList(availableStates).contains(state)){
            String expected = "one of the following: ";
            for (State available: availableStates) expected += available.toString() + ", ";
            throw new InvalidArgumentException(argument, state, 
                expected.substring(0, expected.length()-2));
        }
    }

    //</editor-fold>

}
