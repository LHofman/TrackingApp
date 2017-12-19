package domain;

/**
 * A class for all people.
 * Could be authors, actors, writers...
 */
public class Person extends IEntity{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    private String name;
    private String firstName;
            
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    public Person(String name, String firstName){
        setName(name);
        setFirstName(firstName);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public String getName() {return name;}
    public final void setName(String name) {
        DomainHelper.checkForStringValue("name", name);
        this.name = name;
    }

    public String getFirstName() {return firstName;}
    public final void setFirstName(String firstName) {
        DomainHelper.checkForStringValue("first name", firstName);
        this.firstName = firstName;
    }
    
    //</editor-fold>
    
}
