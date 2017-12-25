package domain;

import domain.item.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.layout.Pane;
import persistence.*;
import persistence.repositories.*;

public final class DomainController extends Observable{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    private static DomainController instance;
    private final Stack<Pane> pathToScene;
    private final Map<Class, Repository> repos;
    private final ObservableList<Item> items;
    private final FilteredList<Item> filteredItems;
    private final Stack<Tuple<Predicate<Item>, Predicate<Item>>> pathToPredicates;
    private final List<Observer> observers;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    private DomainController(){
        repos = new HashMap<>();
        PersistenceController.start();
        PersistenceAdministrator pa = PersistenceController.getPersistenceAdministrator();
        Arrays.stream(new Class[]{Book.class, Game.class, Movie.class, TvShow.class}).forEach(c -> repos.put(c, new ItemRepository(c, pa)));
        Arrays.stream(new Class[]{Person.class}).forEach(c -> repos.put(c, new Repository(c, pa)));
        
        pathToScene = new Stack<>();
        pathToPredicates = new Stack<>();
        observers = new ArrayList<>();
        
        items = FXCollections.observableList(DomainHelper.convertList(getAllFromSuperclass(Item.class), Item.class));
        filteredItems = new FilteredList<>(items);
    }
    
    public static DomainController getInstance(){
        if (instance==null) instance = new DomainController();
        return instance;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="CRUD">
    
    //<editor-fold defaultstate="collapsed" desc="get items">
    
    /**
     * @return A SortedList of all {@link domain.item.Item}
     */
    public SortedList<Item> getAllItems(){
        return getSortedList(DomainHelper.convertList(getAllFromSuperclass(Item.class), Item.class), i -> true);
    }
    
    /**
     * @param <E>
     * @param iClass
     * @return A List of all entities of the specified class and all its subclasses
     */
    public <E extends IEntity> List<E> getAllFromSuperclass(Class iClass) {
        List<E> list = new ArrayList<>();
        repos.keySet().forEach(k -> {
            if (iClass.isAssignableFrom(k))
                list.addAll(getAll(k));
        });
        return list;
    }

    /**
     * @param <E>
     * @param iClass
     * @return A List of all entities of the specified class
     */
    public <E extends IEntity> List<E> getAll(Class iClass){
        return repos.get(iClass).getAll();
    }
    
    /**
     * Adds the predicates to the stack
     * @param items
     * @param listPredicate
     * @return A SortedList of the specified items
     */
    private SortedList<Item> getSortedList(List<Item> items, Predicate<Item> listPredicate){
        pathToPredicates.add(new Tuple<>(listPredicate, i -> true));
        return new SortedList<>(filteredItems);
    }
    
    //</editor-fold>
        
    /**
     * Adds an entity to the database
     * @param entity 
     */
    public void addEntity(IEntity entity){
        repos.get(entity.getClass()).add(entity);
        if (entity instanceof Item) items.add((Item)entity);
    }
    /**
     * Removes an entity from the database
     * @param entity 
     */
    public void deleteEntity(IEntity entity) {
        repos.get(entity.getClass()).delete(entity);
        if (entity instanceof Item) items.remove((Item)entity);
    }
    /**
     * Edits an entity from the database
     * @param entity 
     */
    public void editEntity(IEntity entity) {
        repos.get(entity.getClass()).update(entity);
        if (entity instanceof Item) {
           items.remove((Item)entity);
           items.add((Item)entity);
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="fitler methods">
    
    /**
     * Clears all the filters
     */
    public void clearFilters() {
        filteredItems.setPredicate(pathToPredicates.peek().x);
    }
    
    /**
     * Currently filters:
     * <ul>
     * <li>Type</li>
     * <li>Author (Book)</li>
     * <li>Title</li>
     * <li>ReleaseDateStart</li>
     * <li>ReleaseDateEnd</li>
     * <li>InCollection</li>
     * <li>Status</li>
     * </ul>
     * @param filters 
     */
    public void changeFilter(Map<String, Object> filters){
        String noFilter = "---No Filter---";
        pathToPredicates.get(getScenePathLength()-1).y = i -> {
            for (String key: filters.keySet()){
                Object value = filters.get(key);
                if (value==null) continue;
                String filter = value.toString();
                if (filter.equals(noFilter)) continue;
                switch(key){
                    case "Type":
                        if (!i.getType().toString().equals(filter)) return false;
                        break;
                    case "Author":
                        if (i instanceof Book && !((Book) i).getAuthor().toString().equals(filter)) return false;
                        break;
                    case "Title": 
                        if (!i.getTitle().toLowerCase().contains(filter.toLowerCase())) return false; 
                        break;
                    case "ReleaseDateStart":
                        if (i.getReleaseDate().before(new MyDate((LocalDate)value))) return false;
                        break;
                    case "ReleaseDateEnd":
                        if (i.getReleaseDate().after(new MyDate((LocalDate)value))) return false;
                        break;
                    case "InCollection":
                        Object inCollectionFilter = filters.get("InCollectionFilter");
                        if (inCollectionFilter!=null && Boolean.parseBoolean(inCollectionFilter.toString())==false)break;
                        if (i.isInCollection()!=Boolean.parseBoolean(filter)) return false;
                        break;
                    case "Status":
                        if (!i.getStatus().toString().equals(value)) return false;
                        break;
                }
            }
            return true;
        };
        
        Tuple<Predicate<Item>, Predicate<Item>> predicates = pathToPredicates.peek();
        filteredItems.setPredicate(predicates.x.and(predicates.y));
        
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="homeController methods">
    
    //<editor-fold defaultstate="collapsed" desc="ScenePath">
    
    /**
     * Shows the specified scene
     * @param currentScene 
     */
    public void addToScenePath(Pane currentScene) {
        pathToScene.push(currentScene);
        if (getScenePathLength()>pathToPredicates.size()) {
            pathToPredicates.push(new Tuple<>(i -> true, i -> true));
        }
        notifyObservers(currentScene);
    }
    /**
     * Goes back to the previous scene
     */
    public void removeSceneFromPath(){
        pathToScene.pop();
        pathToPredicates.pop();
        notifyObservers(pathToScene.peek());
    }
    /**
     * @return the amount of scenes still open
     */
    public int getScenePathLength(){
        return pathToScene.size();
    }
    /**
     * Shows the specified scene after closing all open scenes
     * @param currentScene 
     */
    public void setNewPath(Pane currentScene) {
        pathToScene.clear();
        if (!pathToPredicates.isEmpty()){
            Tuple<Predicate<Item>, Predicate<Item>> temp = pathToPredicates.pop();
            pathToPredicates.clear();
            pathToPredicates.push(temp);
        }
        addToScenePath(currentScene);
    }
    
    //</editor-fold>
    
    @Override
    public void addObserver(Observer o){observers.add(o);}
    
    @Override
    public void notifyObservers(Object value){
        observers.forEach(o -> o.update(this, value));
    }
    
    /**
     * Shows the specified message on the screen
     * @param message 
     */
    public void throwException(String message) {
        if (message!=null) notifyObservers("Exception:".concat(message));
    }
    /**
     * Shows the specified exception message on the screen
     * @param exception
     */
    public void throwException(Exception exception){
        throwException(exception.getMessage());
    }
    
    //</editor-fold>

    //</editor-fold>

}
