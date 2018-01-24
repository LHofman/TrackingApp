package domain.entity;

import domain.DomainController;
import domain.DomainHelper;
import domain.MyDate;
import domain.item.TvShow;
import domain.user.UserEpisode;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * A particular episode of a {@link TvShow}
 */
@Entity
public class Episode extends IEntityUser<UserEpisode> implements Serializable{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    @Column(nullable = false)
    private int season;
    @Column(nullable = false)
    private int episodeNr;
    @Column(nullable = false)
    private String title;
    @Temporal(TemporalType.DATE)
    private Calendar releaseDate;
    
    @Transient
    private MyDate myReleaseDate;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private TvShow tvShow;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public Episode(){super();}
    public Episode(int season, int episodeNr, String title, MyDate releaseDate, boolean watched, boolean inCollection){
        this();
        init(season, episodeNr, title, releaseDate);
        setWatched(watched);
        setInCollection(inCollection);
    }
    public Episode(int season, int episodeNr, String title, MyDate releaseDate){
        this(season, episodeNr, title, releaseDate, false, false);
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public int getSeason() {return season;}
    public void setSeason(int season) {
        DomainHelper.checkMin("season", season, 0);
        this.season = season;
    }

    public int getEpisodeNr() {return episodeNr;}
    public void setEpisodeNr(int episodeNr) {
        DomainHelper.checkMin("episode number", episodeNr, 0);
        this.episodeNr = episodeNr;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {
        DomainHelper.checkForStringValue("title", title);
        this.title = title;
    }

    public MyDate getReleaseDate() {
        if (myReleaseDate==null) myReleaseDate = new MyDate(releaseDate);
        return myReleaseDate;
    }
    public void setReleaseDate(Calendar releaseDate) {
        DomainHelper.checkForValue("releaseDate", releaseDate);
        this.releaseDate = releaseDate;
        this.myReleaseDate = new MyDate(releaseDate);
    }
    public void setReleaseDate(MyDate releaseDate) {
        DomainHelper.checkForValue("releaseDate", releaseDate);
        this.myReleaseDate = releaseDate;
        this.releaseDate = releaseDate.getDate();
    }
    
    public boolean isWatched() {
        createUserEntity();
        return userEntity.isWatched();
    }
    public void setWatched(boolean watched) {
        createUserEntity();
        userEntity.setWatched(watched);
    }

    public boolean isInCollection() {
        createUserEntity();
        return userEntity.isInCollection();
    }
    public void setInCollection(boolean inCollection) {
        createUserEntity();
        userEntity.setInCollection(inCollection);
    }

    public TvShow getTvShow(){return tvShow;}
    public void setTvShow(TvShow tvShow){
        DomainHelper.checkForValue("tvShow", tvShow);
        this.tvShow = tvShow;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @Override
    protected void createNewUserEntity(){
        userEntity = new UserEpisode(DomainController.getUser(), this);
    }
    
    /**
     * Sets all the values
     * @param season
     * @param episodeNr
     * @param title
     * @param releaseDate
     */
    protected void init(int season, int episodeNr, String title, MyDate releaseDate){
        setSeason(season);
        setEpisodeNr(episodeNr);
        setTitle(title);
        setReleaseDate(releaseDate);
    }
    
    /**
     * Changes the values
     * @param episode
     */
    public void editEpisode(Episode episode){
        if (episode==null) return;
        init(episode.getSeason(), episode.getEpisodeNr(), episode.getTitle(), episode.getReleaseDate());
    }
    
    @Override
    public String toString(){
        return String.format("%s S%dE%d: %s", tvShow.getTitle(), season, episodeNr, title);
    }
    
    //</editor-fold>

}
