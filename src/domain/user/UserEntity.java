package domain.user;

import domain.DomainHelper;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Superclass to connect Users and Items
 */
@MappedSuperclass
@IdClass(UserEntityId.class)
public abstract class UserEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    
    public UserEntity(){}
    public UserEntity(User user){
        setUser(user);
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        DomainHelper.checkForValue("User", user);
        this.user = user;
    }

}
