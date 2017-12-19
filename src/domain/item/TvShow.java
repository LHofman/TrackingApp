package domain.item;

import domain.MyDate;
import domain.enums.Status;
import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class TvShow extends Item implements Serializable{

    public TvShow(){}
    public TvShow(String title, MyDate releaseDate) {super(title, releaseDate);}
    public TvShow(String title, MyDate releaseDate, boolean inCollection, Status state) {
        super(title, releaseDate, inCollection, state);
    }

    @Override
    protected Status[] getAvailableStates() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.OnTrack, Status.Done};
    }
    
}
