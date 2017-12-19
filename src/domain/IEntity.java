package domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The superclass for all entity classes
 */
@MappedSuperclass
public abstract class IEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public IEntity(){}
    
    public void setId(int id){
        DomainHelper.checkMin("id", id, 1);
        this.id = id;
    }
    public int getId(){return id;}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IEntity other = (IEntity) obj;
        return this.hashCode() == other.hashCode();
    }
    
    
    
}
