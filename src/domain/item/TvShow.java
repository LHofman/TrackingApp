package domain.item;

import domain.DomainController;
import domain.MyDate;
import domain.entity.Episode;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.user.UserTvShow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TvShow extends Item<UserTvShow> implements Serializable{

    private boolean ongoing;
    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.PERSIST)
    private List<Episode> episodes;
    
    public TvShow(){super();}
    public TvShow(String title, MyDate releaseDate, boolean ongoing){this(title, releaseDate, ongoing, new ArrayList<>());}
    public TvShow(String title, MyDate releaseDate, boolean ongoing, boolean inCollection, Status status) {this(title, releaseDate, ongoing, inCollection, status, new ArrayList<>());}
    public TvShow(String title, MyDate releaseDate, boolean ongoing, List<Episode> episodes) {
        this();
        init(title, releaseDate, ongoing, episodes);
    }
    public TvShow(String title, MyDate releaseDate, boolean ongoing, boolean inCollection, Status status, List<Episode> episodes) {
        super(title, releaseDate, inCollection, status);
        setOngoing(ongoing);
        setEpisodes(episodes);
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public List<Episode> getEpisodes() {
        return episodes;
    }
    public List<Episode> getEpisodes(int season){
        List<Episode> episodes = new ArrayList<>();
        for (Episode episode: this.episodes)
            if (episode.getSeason()==season)
                episodes.add(episode);
        return episodes;
    }
    public void setEpisodes(List<Episode> episodes) {
        if (episodes==null) this.episodes = new ArrayList<>();
        else {
            this.episodes = new ArrayList<>(episodes);
            for (Episode episode: episodes) episode.setTvShow(this);
            sortEpisodes();
        }
    }

    public List<Integer> getSeasons(){
        Set<Integer> seasons = new HashSet<>();
        for (Episode episode: this.episodes)
            seasons.add(episode.getSeason());
        return new ArrayList<>(seasons);
    }
    
    public boolean isOngoing(){return ongoing;}
    public void setOngoing(boolean ongoing){this.ongoing = ongoing;}
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @Override
    protected void createNewUserEntity(){
        userEntity = new UserTvShow(DomainController.getUser(), this);
    }
    
    /**
     * Sets all the values
     * @param title
     * @param releaseDate
     * @param onGoing
     * @param objectives 
     */
    private void init(String title, MyDate releaseDate, boolean ongoing, List<Episode> episodes){
        init(title, releaseDate);
        setOngoing(ongoing);
        setEpisodes(episodes);
    }
    
    /**
     * Sets all the values to the specified Game
     * @param tvShow 
     */
    public void editTvShow(TvShow tvShow){
        if (tvShow==null) return;
        init(tvShow.getTitle(), tvShow.getReleaseDate(), tvShow.isOngoing(), tvShow.getEpisodes());
    }
    
    /**
     * Adds the episodes
     * @param episodes 
     */
    public void addEpisodes(Episode... episodes){
        if (episodes==null) return;
        for (Episode episode: episodes){
            episode.setTvShow(this);
            this.episodes.add(episode);
        }
        sortEpisodes();
    }
    
    /**
     * Removes the episodes
     * @param episodes 
     */
    public void removeEpisodes(Episode... episodes){
        if (episodes==null) return;
        for (Episode episode: episodes)
            this.episodes.remove(episode);
    }
    
    /**
     * @param season
     * @return whether every episodes in the specified season is watched
     */
    public boolean isWatched(int season){
        for (Episode episode: getEpisodes(season))
            if (!episode.isWatched())
                return false;
        return true;
    }
    
    /**
     * @param season
     * @return whether every episodes in the specified season is in collection
     */
    public boolean isCollected(int season){
        for (Episode episode: getEpisodes(season))
            if (!episode.isInCollection())
                return false;
        return true;
    }
    
    /**
     * Sorts {@link #episodes} by episode number
     */
    private void sortEpisodes(){
        Collections.sort(episodes, new Comparator<Episode>() {
            @Override
            public int compare(Episode t, Episode t1) {
                int seasonDiff = t.getSeason() - t1.getSeason();
                return seasonDiff!=0 ? seasonDiff : t.getEpisodeNr() - t1.getEpisodeNr();
            }
        });
    }
    
    @Override
    public ItemType getType() {
        return ItemType.TvShow;
    }
    
    //</editor-fold>
    
}
