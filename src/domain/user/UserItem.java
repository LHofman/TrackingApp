package domain.user;

import domain.DomainHelper;
import domain.enums.Status;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;

/**
 * Superclass to connect Users and Items
 */
@MappedSuperclass
@IdClass(UserEntityId.class)
public abstract class UserItem extends UserEntity{
    
    private boolean inCollection;
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public UserItem(){}
    public UserItem(User user, boolean inCollection, Status status){
        super(user);
        setInCollection(inCollection);
        setStatus(status);
    }

    public boolean isInCollection() {
        return inCollection;
    }

    public void setInCollection(boolean inCollection) {
        this.inCollection = inCollection;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        DomainHelper.checkForStateValue("Status", status, getAvailableStatuses());
        this.status = status;
    }
    
    /**
     * @return the available statuses for this item
     */
    public Status[] getAvailableStatuses(){
        return new Status[]{Status.ToDo, Status.Done};
    }
    
}
