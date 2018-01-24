package domain.user;

import java.io.Serializable;

/**
 * Primary Key for the UserItem classes
 */
public class UserEntityId implements Serializable{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    private int user;
    private int entity;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getItem() {
        return entity;
    }

    public void setItem(int item) {
        this.entity = item;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.user;
        hash = 37 * hash + this.entity;
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
        final UserEntityId other = (UserEntityId) obj;
        if (this.user != other.user) {
            return false;
        }
        if (this.entity != other.entity) {
            return false;
        }
        return true;
    }
    
    //</editor-fold>
    
}
