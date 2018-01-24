package domain.user;

import domain.DomainHelper;
import domain.enums.Status;
import domain.item.Movie;
import domain.item.TvShow;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Connects User and Book
 */
@Entity
public class UserTvShow extends UserItem implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "tvShowId", referencedColumnName = "id")
    private TvShow entity;
    
    public UserTvShow(){}
    public UserTvShow(User user, TvShow tvShow){this(user, tvShow, false, Status.ToDo);}
    public UserTvShow(User user, TvShow tvShow, boolean inCollection, Status status){
        super(user, inCollection, status);
        setTvShow(tvShow);
    }
    
    public TvShow getTvShow(){return entity;}
    public void setTvShow(TvShow tvShow){
        DomainHelper.checkForValue("Book", tvShow);
        this.entity = tvShow;
    }
    
    /**
     * @return the available statuses for this item
     */
    @Override
    public Status[] getAvailableStatuses() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.Done};
    }
    
}
