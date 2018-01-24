package domain.item;

import domain.DomainHelper;
import domain.MyDate;
import domain.entity.IEntityUser;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.user.UserItem;
import java.util.Calendar;
import javax.persistence.Column;
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
 * @param <T> subclass of {@link UserItem}
 */
@MappedSuperclass
public abstract class Item<T extends UserItem> extends IEntityUser<T>{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar releaseDate;
    
    @Transient
    private MyDate myReleaseDate;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public Item(){super();}
    public Item(String title, MyDate releaseDate){
        this();
        init(title, releaseDate);
    }
    public Item(String title, MyDate releaseDate, boolean inCollection, Status status){
        this(title, releaseDate);
        setInCollection(inCollection);
        setStatus(status);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public String getTitle() {return title;}
    public void setTitle(String title) {
        DomainHelper.checkForStringValue("title", title);
        this.title = title;
    }

    public MyDate getReleaseDate() {
        if (myReleaseDate==null) myReleaseDate = new MyDate(releaseDate);
        return myReleaseDate;
    }
    public void setReleaseDate(Calendar releaseDate){
        DomainHelper.checkForValue("releaseDate", releaseDate);
        this.releaseDate = releaseDate;
        this.myReleaseDate = new MyDate(releaseDate);
    }
    public void setReleaseDate(MyDate releaseDate) {
        DomainHelper.checkForValue("releaseDate", releaseDate);
        this.myReleaseDate = releaseDate;
        this.releaseDate = releaseDate.getDate();
    }

    public Status getStatus(){
        createUserEntity();
        return userEntity.getStatus();
    }
    public void setStatus(Status status){
        createUserEntity();
        userEntity.setStatus(status);
    }
    
    public boolean isInCollection(){
        createUserEntity();
        return userEntity.isInCollection();
    }
    public void setInCollection(boolean inCollection){
        createUserEntity();
        userEntity.setInCollection(inCollection);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    /**
     * Sets all the values
     * @param title
     * @param releaseDate
     */
    protected void init(String title, MyDate releaseDate){
        setTitle(title);
        setReleaseDate(releaseDate);
    }
    
    /**
     * Changes the values
     * @param item 
     */
    public void editItem(Item item){
        if (item==null || item.getType()!=getType()) return;
        init(item.getTitle(), item.getReleaseDate());
    }
    
    /**
     * @return the name of the subclass
     */
    public abstract ItemType getType();
    
    @Override
    public String toString() {
        return String.format("%s (%s)", title, getReleaseDate().getYear());
    }

    //</editor-fold>

}
