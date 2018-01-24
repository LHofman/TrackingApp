package domain.entity;

import domain.DomainController;
import domain.user.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * The superclass for all entity classes
 * @param <T>
 */
@MappedSuperclass
public abstract class IEntityUser<T extends UserEntity> extends IEntity{
    
    @OneToMany(mappedBy = "entity", cascade = CascadeType.PERSIST)
    protected List<T> userEntities;
    @Transient
    protected T userEntity;

    public IEntityUser(){
        userEntities = new ArrayList<>();
    }

    public List<T> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<T> userEntities) {
        this.userEntities = userEntities;
    }
    
    protected void createUserEntity(){
        if (this.userEntity==null)
            for (T userEntity: userEntities)
                if (userEntity.getUser().equals(DomainController.getUser()))
                    this.userEntity = userEntity;
        if (this.userEntity == null){
            createNewUserEntity();
            this.userEntities.add(userEntity);
        }
    }
    protected abstract void createNewUserEntity();
}
