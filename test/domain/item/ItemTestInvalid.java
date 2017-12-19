package domain.item;

import domain.item.Item;
import domain.MyDate;
import domain.enums.State;
import exceptions.InvalidArgumentException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Invalid test cases for {@link domain.Item.Item}
 */
@RunWith(Parameterized.class)
public class ItemTestInvalid {
    
    private Item item;
    private final String title;
    private final MyDate releaseDate;
    private final boolean inCollection;
    private final State state;
    
    @Parameters
    public static Collection<Object[]> getTestParameters(){
        return Arrays.asList(new Object[][]{
            {null, new MyDate(1970, 1, 1), true, State.Done},
            {"", new MyDate(1970, 1, 1), true, State.Done},
            {"    ", new MyDate(1970, 1, 1), true, State.Done},
            {"Silence of the Lambs", new MyDate(1970, 1, 1), true, null},
            {"Silence of the Lambs", new MyDate(1970, 1, 1), true, State.Doing}
        });
    }
    
    public ItemTestInvalid(String title, MyDate releaseDate, boolean inCollection, State state){
        this.title = title;
        this.releaseDate = releaseDate;
        this.inCollection = inCollection;
        this.state = state;
    }
    
    @Test(expected = InvalidArgumentException.class)
    public void testItemValid(){
        item = new ItemImpl(title, releaseDate, inCollection, state);
    }
    
    private class ItemImpl extends Item{
        public ItemImpl(String title, MyDate releaseDate, boolean inCollection, State state) {
            super(title, releaseDate, inCollection, state);
        }
        @Override
        protected State[] getAvailableStates() {
            return new State[]{State.ToDo, State.Done};
        }
    }
}
