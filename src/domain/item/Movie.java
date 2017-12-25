package domain.item;

import domain.MyDate;
import domain.enums.ItemType;
import domain.enums.Status;
import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Movie extends Item implements Serializable {

    public Movie(){}
    public Movie(String title, MyDate releaseDate){super(title, releaseDate);}
    public Movie(String title, MyDate releaseDate, boolean inCollection, Status state) {
        super(title, releaseDate, inCollection, state);
    }

    @Override
    public ItemType getType() {
        return ItemType.Movie;
    }
    
}
