package domain.item;

import domain.MyDate;
import domain.entity.GameObjective;
import domain.enums.ItemType;
import domain.enums.Status;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Game extends Item implements Serializable{
    
    @OneToMany(mappedBy = "game")
    private List<GameObjective> objectives;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">

    public Game(){}
    public Game(String title, MyDate releaseDate) {
        this(title, releaseDate, false, null);
    }
    public Game(String title, MyDate releaseDate, boolean inCollection, Status status) {
        this(title, releaseDate, inCollection, status, new ArrayList<>());
    }
    public Game(String title, MyDate releaseDate, boolean inCollection, Status status, List<GameObjective> objectives){
        init(title, releaseDate, inCollection, status, objectives);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public List<GameObjective> getObjectives() {
        List<GameObjective> objectives = new ArrayList<>();
        for (GameObjective objective: this.objectives)
            if (objective.getParent()==null)
                objectives.add(objective);
        return objectives;
    }
    public void setObjectives(List<GameObjective> objectives) {
        if (objectives==null) this.objectives = new ArrayList<>();
        else this.objectives = new ArrayList<>(objectives);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    /**
     * Sets all the values
     * @param title
     * @param releaseDate
     * @param inCollection
     * @param status
     * @param objectives 
     */
    private void init(String title, MyDate releaseDate, boolean inCollection, Status status, List<GameObjective> objectives){
        setTitle(title);
        setReleaseDate(releaseDate);
        setInCollection(inCollection);
        setStatus(status);
        setObjectives(objectives);
    }
    
    /**
     * Sets all the values to the specified Game
     * @param game 
     */
    public void editGame(Game game){
        if (game==null) return;
        init(game.getTitle(), game.getReleaseDate(), game.isInCollection(), game.getStatus(), game.getObjectives());
    }
    
    /**
     * Adds the objectives
     * @param objectives 
     */
    public void addObjectives(GameObjective... objectives){
        if (objectives==null) return;
        for (GameObjective objective: objectives){
            objective.setGame(this);
            this.objectives.add(objective);
        }
    }
    /**
     * Removes the objectives
     * @param objectives 
     */
    public void removeObjectives(GameObjective... objectives){
        if (objectives==null) return;
        for (GameObjective objective: objectives)
            this.objectives.remove(objective);
    }
    
    /**
     * @return the available statuses for this item
     */
    @Override
    public Status[] getAvailableStatuses() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.Done};
    }
    
    /**
     * @return the {@link domain.enums.ItemType}
     */
    @Override
    public ItemType getType() {
        return ItemType.Game;
    }
    
    //</editor-fold>

}
