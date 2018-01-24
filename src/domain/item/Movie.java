package domain.item;

import domain.DomainController;
import domain.MyDate;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.user.UserMovie;
import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Movie extends Item<UserMovie> implements Serializable {

    public Movie(){super();}
    public Movie(String title, MyDate releaseDate) {
        super(title, releaseDate);
    }
    public Movie(String title, MyDate releaseDate, boolean inCollection, Status status) {
        super(title, releaseDate, inCollection, status);
    }

    @Override
    protected void createNewUserEntity(){
        userEntity = new UserMovie(DomainController.getUser(), this);
    }
    
    @Override
    public ItemType getType() {
        return ItemType.Movie;
    }
    
}
