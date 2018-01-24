package domain.user;

import domain.DomainHelper;
import domain.enums.Status;
import domain.item.Book;
import domain.item.Game;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Connects User and Book
 */
@Entity
public class UserGame extends UserItem implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "gameId", referencedColumnName = "id")
    private Game entity;
    
    public UserGame(){}
    public UserGame(User user, Game game){this(user, game, false, Status.ToDo);}
    public UserGame(User user, Game game, boolean inCollection, Status status){
        super(user, inCollection, status);
        setGame(game);
    }
    
    public Game getGame(){return entity;}
    public void setGame(Game game){
        DomainHelper.checkForValue("Book", game);
        this.entity = game;
    }
    
    /**
     * @return the available statuses for this item
     */
    @Override
    public Status[] getAvailableStatuses() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.Done};
    }
    
}
