package domain.user;

import domain.DomainHelper;
import domain.entity.GameObjective;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Connects User and Book
 */
@Entity
public class UserGameObjective extends UserEntity implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "gameObjectiveId", referencedColumnName = "id")
    private GameObjective entity;
    
    private boolean completed;

    public UserGameObjective(){}
    public UserGameObjective(User user, GameObjective gameObjective){this(user, gameObjective, false);}
    public UserGameObjective(User user, GameObjective gameObjective, boolean completed){
        super(user);
        setGameObjective(gameObjective);
        setCompleted(completed);
    }

    public GameObjective getGameObjective() {
        return entity;
    }

    public void setGameObjective(GameObjective gameObjective) {
        DomainHelper.checkForValue("Game Objective", gameObjective);
        this.entity = gameObjective;
    }
    
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
