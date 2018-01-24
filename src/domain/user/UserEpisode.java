package domain.user;

import domain.entity.Episode;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Connects User and Book
 */
@Entity
public class UserEpisode extends UserEntity implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "episodeId", referencedColumnName = "id")
    private Episode entity;
    
    private boolean inCollection;
    private boolean watched;

    public UserEpisode(){}
    public UserEpisode(User user, Episode episode){this(user, episode, false, false);}
    public UserEpisode(User user, Episode episode, boolean inCollection, boolean watched){
        super(user);
        setEpisode(episode);
        setInCollection(inCollection);
        setWatched(watched);
    }

    public Episode getEpisode() {
        return entity;
    }

    public void setEpisode(Episode episode) {
        this.entity = episode;
    }
    
    public boolean isInCollection() {
        return inCollection;
    }

    public void setInCollection(boolean completed) {
        this.inCollection = completed;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

}
