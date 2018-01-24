package domain.item;

import domain.DomainController;
import domain.MyDate;
import domain.entity.GameObjective;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.user.UserGame;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Game extends Item<UserGame> implements Serializable{
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST)
    private List<GameObjective> objectives;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">

    public Game(){super();}
    public Game(String title, MyDate releaseDate) {
        this(title, releaseDate, new ArrayList<>());
    }
    public Game(String title, MyDate releaseDate, boolean inCollection, Status status) {
        this(title, releaseDate, inCollection, status, new ArrayList<>());
    }
    public Game(String title, MyDate releaseDate, List<GameObjective> objectives){
        this();
        init(title, releaseDate, objectives);
    }
    public Game(String title, MyDate releaseDate, boolean inCollection, Status status, List<GameObjective> objectives){
        super(title, releaseDate, inCollection, status);
        setObjectives(objectives);
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
    
    @Override
    protected void createNewUserEntity(){
        userEntity = new UserGame(DomainController.getUser(), this);
    }
    
    /**
     * Sets all the values
     * @param title
     * @param releaseDate
     * @param objectives 
     */
    private void init(String title, MyDate releaseDate, List<GameObjective> objectives){
        init(title, releaseDate);
        setObjectives(objectives);
    }
    
    /**
     * Sets all the values to the specified Game
     * @param game 
     */
    public void editGame(Game game){
        if (game==null) return;
        init(game.getTitle(), game.getReleaseDate(), game.getObjectives());
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
     * @return the {@link domain.enums.ItemType}
     */
    @Override
    public ItemType getType() {
        return ItemType.Game;
    }
    
    //</editor-fold>

}
