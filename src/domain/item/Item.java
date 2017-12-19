package domain.item;

import domain.DomainHelper;
import domain.IEntity;
import domain.MyDate;
import domain.enums.State;

/**
 * Superclass for all item classes
 * <ul>
 * <li>{@link Book}</li>
 * <li>{@link Game}</li>
 * <li>{@link Movie}</li>
 * <li>{@link TvShow}</li>
 * </ul>
 */
public abstract class Item extends IEntity{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    private String title;
    private MyDate releaseDate;
    private boolean inCollection;
    private State state;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    public Item(String title, MyDate releaseDate){
        this(title, releaseDate, false, null);
    }
    public Item(String title, MyDate releaseDate, boolean inCollection, State state){
        setTitle(title);
        setReleaseDate(releaseDate);
        setInCollection(inCollection);
        setState(state);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public String getTitle() {return title;}
    public final void setTitle(String title) {
        DomainHelper.checkForStringValue("title", title);
        this.title = title;
    }

    public MyDate getReleaseDate() {return releaseDate;}
    public final void setReleaseDate(MyDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isInCollection() {return inCollection;}
    public final void setInCollection(boolean inCollection) {
        this.inCollection = inCollection;
    }

    public State getState() {return state;}
    public final void setState(State state) {
        if (state==null) state=State.ToDo;
        DomainHelper.checkForStateValue("state", state, getAvailableStates());
        this.state = state;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    protected abstract State[] getAvailableStates();
    //</editor-fold>

}
