package persistence;

import domain.entity.IEntity;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * The administrator of all the entity classes
 */

public class PersistenceAdministrator{
    
    private final EntityManager manager;
    private final EntityTransaction transaction;
    
    public PersistenceAdministrator(EntityManager manager){
        this.manager = manager;
        this.transaction = manager.getTransaction();
    }
    
    public <E extends IEntity> E getFromId(Class<E> eClass, int id){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(eClass);
        cq.where(cb.equal(e.get("id"), id));
        Query query = manager.createQuery(cq);
        try{
            return (E)query.getSingleResult();
        }catch(Exception ex){
            return null;
        }
    }

    public <E extends IEntity> List<E> getAll(Class<E> eClass){
        return manager.createQuery(manager.getCriteriaBuilder().createQuery(eClass)).getResultList();
    }

    public <E extends IEntity> void persist(E... objects) {
        doTransaction(manager::persist, objects);
    }

    public <E extends IEntity> void remove(E... objects) {
        doTransaction(manager::remove, objects);
    }

    public <E extends IEntity> void merge(E... objects) {
        doTransaction(manager::merge, objects);
    }
    
    /**
     * Completes the transaction for the objects
     * @param <E>
     * @param transaction
     * @param objects 
     */
    private <E extends IEntity> void doTransaction(Consumer<E> transaction, E... objects){
        if (!this.transaction.isActive()) this.transaction.begin();
        
        Arrays.stream(objects).forEach(o -> transaction.accept(o));
        this.transaction.commit();
    }

}
