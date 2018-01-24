package domain.entity;

import domain.DomainController;
import domain.DomainHelper;
import domain.item.Game;
import domain.user.UserEpisode;
import domain.user.UserGameObjective;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

/**
 * An object of {@link domain.item.Game}
 */
@Entity
public class GameObjective extends IEntityUser<UserGameObjective> implements Serializable{

    @Column(name = "objectiveIndex")
    private int index;
    private String objective;
    @ElementCollection
    @JoinTable(name = "GAME_OBJECTIVE", joinColumns = @JoinColumn(name = "ID"))
    @MapKeyColumn(name = "LINK_NAME")
    @Column(name = "LINK")
    private Map<String, String> links;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private Game game;
    @JoinColumn(nullable = true)
    @ManyToOne
    private GameObjective parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<GameObjective> objectives;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    public GameObjective() {super();}

    public GameObjective(int index, String objective, Map<String, String> links, List<GameObjective> objectives) {
        this();
        init(index, objective, links, objectives);
    }

    public GameObjective(int index, String objective, boolean completed) {
        this(index, objective, new HashMap<>(), new ArrayList<>());
        setCompleted(completed);
    }

    public GameObjective(int index, String objective) {
        this(index, objective, new HashMap(), new ArrayList<>());
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        DomainHelper.checkForStringValue("objective", objective);
        this.objective = objective;
    }

    public boolean isCompleted() {
        createUserEntity();
        return userEntity.isCompleted();
    }

    public void setCompleted(boolean completed) {
        createUserEntity();
        userEntity.setCompleted(completed);
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        if (links == null) {
            this.links = new HashMap<>();
        } else {
            this.links = links;
        }
    }
    
    public void setGame(Game game){
        if (game==null)return;
        this.game = game;
    }
    public Game getGame(){
        return game;
    }
    
    public List<GameObjective> getObjectives(){ return new ArrayList<>(objectives);}
    public void setObjectives(List<GameObjective> objectives){
        if (objectives==null) this.objectives = new ArrayList<>();
        else this.objectives = new ArrayList<>(objectives);
    }
    
    public GameObjective getParent(){return parent;}
    public void setParent(GameObjective objective){this.parent = objective;}
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @Override
    protected void createNewUserEntity(){
        userEntity = new UserGameObjective(DomainController.getUser(), this);
    }
    
    /**
     * Sets all the values
     *
     * @param index
     * @param objective
     */
    private void init(int index, String objective, Map<String, String> links, List<GameObjective> objectives) {
        setIndex(index);
        setObjective(objective);
        setLinks(links);
        setObjectives(objectives);
    }

    /**
     * Sets all the values to the values of the specified gameObjective
     *
     * @param gameObjective
     */
    public void editGameObjective(GameObjective gameObjective) {
        if (gameObjective == null) {
            return;
        }
        init(gameObjective.getIndex(), gameObjective.getObjective(), gameObjective.getLinks(), gameObjective.getObjectives());
    }

    /**
     * Adds a link
     *
     * @param name
     * @param link
     */
    public void addLink(String name, String link) {
        DomainHelper.checkForStringValue("name", name);
        DomainHelper.checkForStringValue("link", link);
        links.put(name, link);
    }

    /**
     * Removes links
     *
     * @param names
     */
    public void removeLinks(String... names) {
        if (names == null) {
            return;
        }
        for (String name : names) {
            links.remove(name);
        }
    }
    
    /**
     * Adds Sub-Objectives to this GameObjective
     * @param objectives
     */
    public void addObjectives(GameObjective... objectives){
        if (objectives==null) return;
        for (GameObjective objective: objectives){
            objective.setGame(game);
            objective.setParent(this);
            this.objectives.add(objective);
        }
    }
    /**
     * Removes Sub-Objectives from this GameObjective
     * @param objectives 
     */
    public void removeObjectives(GameObjective... objectives){
        if (objectives==null) return;
        this.objectives.removeAll(Arrays.asList(objectives));
    }

    /**
     * @return Whether this GameObjective has any Sub-Objectives
     */
    public boolean hasObjectives() {
        return !objectives.isEmpty();
    }
    
    /**
     * Opens a link with your default browser
     *
     * @param name
     */
    public void openLink(String name) {
        if (name == null || !links.containsKey(name)) {
            return;
        }
        DomainHelper.openLink(links.get(name));
    }
    
    @Override
    public String toString(){
        return objective;
    }

    //</editor-fold>

}
