package persistence;

import domain.IEntity;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
    
    public <E extends IEntity> List<E> getAll(Class<E> eClass){
        return manager.createQuery(manager.getCriteriaBuilder().createQuery(eClass)).getResultList();
    }

    public <E extends IEntity> void persist(E... objects) {
        doTransaction(manager::persist, objects);
        Arrays.stream(objects).forEach(o -> manager.persist(o));
        manager.getTransaction().commit();
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
