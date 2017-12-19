package domain.item;

import domain.DomainHelper;
import domain.IEntity;
import domain.MyDate;
import domain.enums.Status;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Superclass for all item classes
 * <ul>
 * <li>{@link Book}</li>
 * <li>{@link Game}</li>
 * <li>{@link Movie}</li>
 * <li>{@link TvShow}</li>
 * </ul>
 */
@MappedSuperclass
public abstract class Item extends IEntity{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, name = "releaseDate")
    @Temporal(TemporalType.DATE)
    private Calendar releaseDate;
    private boolean inCollection;
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Transient
    private MyDate myReleaseDate;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public Item(){}
    public Item(String title, MyDate releaseDate){
        this(title, releaseDate, false, null);
    }
    public Item(String title, MyDate releaseDate, boolean inCollection, Status state){
        setTitle(title);
        setReleaseDate(releaseDate);
        setInCollection(inCollection);
        setStatus(state);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public String getTitle() {return title;}
    public void setTitle(String title) {
        DomainHelper.checkForStringValue("title", title);
        this.title = title;
    }

    public MyDate getReleaseDate() {return myReleaseDate;}
    public void setReleaseDate(MyDate releaseDate) {
        DomainHelper.checkForValue("releaseDate", releaseDate);
        this.myReleaseDate = releaseDate;
        this.releaseDate = releaseDate.getDate();
    }

    public boolean isInCollection() {return inCollection;}
    public void setInCollection(boolean inCollection) {
        this.inCollection = inCollection;
    }

    public Status getStatus() {return status;}
    public void setStatus(Status status) {
        if (status==null) status=Status.ToDo;
        DomainHelper.checkForStateValue("state", status, getAvailableStates());
        this.status = status;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    protected abstract Status[] getAvailableStates();
    //</editor-fold>

}
