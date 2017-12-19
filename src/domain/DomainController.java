package domain;

import domain.item.*;
import java.util.*;
import persistence.*;
import persistence.repositories.*;

public class DomainController extends Observable{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    private final Map<Class, Repository> repos;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public DomainController(){
        repos = new HashMap<>();
        PersistenceController.start();
        PersistenceAdministrator pa = PersistenceController.getPersistenceAdministrator();
        Arrays.stream(new Class[]{Book.class, Game.class, Movie.class, TvShow.class}).forEach(c -> repos.put(c, new ItemRepository(c, pa)));
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="CRUD">
    
    //<editor-fold defaultstate="collapsed" desc="get items">
    
    public List<Item> getAll(Class iClass){
        return repos.get(iClass).getAll();
    }
    
    //</editor-fold>
        
    public void addEntity(IEntity entity){
        repos.get(entity.getClass()).add(entity);
    }

    public void deleteEntity(IEntity entity) {
        repos.get(entity.getClass()).delete(entity);
    }

    public void editEntity(IEntity entity) {
        repos.get(entity.getClass()).update(entity);
    }
    
    //</editor-fold>
    
    //</editor-fold>    

}
