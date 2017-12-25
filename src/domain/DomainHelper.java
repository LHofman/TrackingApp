package domain;

import domain.enums.ItemType;
import domain.enums.Status;
import domain.item.Book;
import domain.item.Game;
import domain.item.Movie;
import domain.item.TvShow;
import exceptions.InvalidArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class DomainHelper {

    //<editor-fold defaultstate="collapsed" desc="check for valid argument">
    //<editor-fold defaultstate="collapsed" desc="check for value">
    /**
     * Checks if the value is null
     *
     * @param argument the name of the argument
     * @param value
     */
    public static void checkForValue(String argument, Object value) {
        if (value == null) {
            throw new InvalidArgumentException(argument);
        }
    }

    /**
     * Checks if the value is null or empty
     *
     * @param argument the name of the argument
     * @param value
     */
    public static void checkForStringValue(String argument, String value) {
        checkForValue(argument, value);
        if (value.trim().isEmpty()) {
            throw new InvalidArgumentException(argument);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="check for range">
    /**
     * Checks if the value is higher or equal to the specified parameter
     *
     * @param argument the name of the argument
     * @param value
     * @param min
     */
    public static void checkMin(String argument, int value, int min) {
        if (value < min) {
            throw new InvalidArgumentException(argument, value, "at least " + min);
        }
    }

    /**
     * Checks if the value is lower or equal to the specified parameter
     *
     * @param argument the name of the argument
     * @param value
     * @param max
     */
    public static void checkMax(String argument, int value, int max) {
        if (value > max) {
            throw new InvalidArgumentException(argument, value, "at most " + max);
        }
    }

    /**
     * Checks if the value is in range
     *
     * @param argument the name of the argument
     * @param value
     * @param min
     * @param max
     */
    public static void checkRange(String argument, int value, int min, int max) {
        checkMin(argument, value, min);
        checkMax(argument, value, max);
    }

    //</editor-fold>
    /**
     * Checks if the values form a valid date
     *
     * @param year
     * @param month
     * @param day
     */
    static void checkForDate(int year, int month, int day) {
        checkRange("year", year, 1, 3000);
        checkRange("month", month, 1, 12);
        checkRange("day", day, 1, new GregorianCalendar(year, month - 1, 1).getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * Checks if the value is a valid state
     *
     * @param argument the name of the argument
     * @param state
     * @param availableStates
     */
    public static void checkForStateValue(String argument, Status state, Status[] availableStates) {
        checkForValue(argument, state);
        if (!Arrays.asList(availableStates).contains(state)) {
            String expected = "one of the following: ";
            for (Status available : availableStates) {
                expected += available.toString() + ", ";
            }
            throw new InvalidArgumentException(argument, state,
                    expected.substring(0, expected.length() - 2));
        }
    }

    //</editor-fold>
    /**
     * @param <T>
     * @param <E>
     * @param toConvert
     * @param clazz
     * @return
     */
    public static <T, E> List<E> convertList(List<T> toConvert, Class<E> clazz) {
        List<E> convertedList = new ArrayList<>();
        if (toConvert != null) {
            toConvert.forEach(t -> {
                try {
                    convertedList.add(clazz.cast(t));
                } catch (ClassCastException e) {
                }
            });
        }
        return convertedList;
    }

    /**
     * @param type
     * @return the statuses available for the specified type
     */
    public static Status[] getAvailableStatuses(ItemType type) {
        if (type==null) return null;
        switch (type) {
            case Book:
                return new Book().getAvailableStatuses();
            case Game:
                return new Game().getAvailableStatuses();
            case Movie:
                return new Movie().getAvailableStatuses();
            case TvShow:
                return new TvShow().getAvailableStatuses();
            default:
                return new Status[0];
        }
    }

}
