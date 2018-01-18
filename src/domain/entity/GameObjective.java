package domain.entity;

import domain.DomainHelper;
import domain.item.Game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class GameObjective extends IEntity implements Serializable{

    @Column(name = "objectiveIndex")
    private int index;
    private String objective;
    private boolean completed;
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
    @OneToMany(mappedBy = "parent")
    private List<GameObjective> objectives;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    public GameObjective() {
    }

    public GameObjective(int index, String objective, boolean completed, Map<String, String> links, List<GameObjective> objectives) {
        init(index, objective, completed, links, objectives);
    }

    public GameObjective(int index, String objective, boolean completed) {
        this(index, objective, completed, new HashMap<>(), new ArrayList<>());
    }

    public GameObjective(int index, String objective) {
        this(index, objective, false);
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
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
    
    public List<GameObjective> getObjectives(){ return objectives;}
    public void setObjectives(List<GameObjective> objectives){
        if (objectives==null) this.objectives = new ArrayList<>();
        else this.objectives = new ArrayList<>(objectives);
    }
    
    public GameObjective getParent(){return parent;}
    public void setParent(GameObjective objective){this.parent = objective;}
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Sets all the values
     *
     * @param index
     * @param objective
     * @param completed
     */
    private void init(int index, String objective, boolean completed, Map<String, String> links, List<GameObjective> objectives) {
        setIndex(index);
        setObjective(objective);
        setCompleted(completed);
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
        init(gameObjective.getIndex(), gameObjective.getObjective(), gameObjective.isCompleted(), gameObjective.getLinks(), gameObjective.getObjectives());
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
