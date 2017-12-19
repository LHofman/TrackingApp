package domain.item;

import domain.MyDate;
import domain.enums.State;

public class Game extends Item{

    public Game(String title, MyDate releaseDate) {super(title, releaseDate);}
    public Game(String title, MyDate releaseDate, boolean inCollection, State state) {
        super(title, releaseDate, inCollection, state);
    }

    @Override
    protected State[] getAvailableStates() {
        return new State[]{State.ToDo, State.Doing, State.OnHold, State.Done};
    }
    
}
