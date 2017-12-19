package persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The controller which manages the factory and manager
 */
public class PersistenceController {
    
    private static EntityManagerFactory factory;
    private static EntityManager manager;
    private static PersistenceAdministrator administrator;
    
    public static void start(){
        factory = Persistence.createEntityManagerFactory("TrackingAppPU");
        manager = factory.createEntityManager();
        administrator = new PersistenceAdministrator(manager);
    }
    
    public static void close(){
        manager.close();
        factory.close();
    }
    
    public static PersistenceAdministrator getPersistenceAdministrator(){
        return administrator;
    }
    
}
