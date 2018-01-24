package domain.user;

import domain.DomainHelper;
import domain.enums.Status;
import domain.item.Game;
import domain.item.Movie;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Connects User and Book
 */
@Entity
public class UserMovie extends UserItem implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "movieId", referencedColumnName = "id")
    private Movie entity;
    
    public UserMovie(){}
    public UserMovie(User user, Movie movie){this(user, movie, false, Status.ToDo);}
    public UserMovie(User user, Movie movie, boolean inCollection, Status status){
        super(user, inCollection, status);
        setMovie(movie);
    }
    
    public Movie getMovie(){return entity;}
    public void setMovie(Movie movie){
        DomainHelper.checkForValue("Book", movie);
        this.entity = movie;
    }
    
}
