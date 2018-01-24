package domain.user;

import domain.DomainHelper;
import domain.entity.IEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * User class
 */
@Entity
public class User extends IEntity implements Serializable{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String username;
    
    @OneToMany(mappedBy = "user")
    private List<UserBook> userBooks;
    
    @OneToMany(mappedBy = "user")
    private List<UserGame> userGames;
    
    @OneToMany(mappedBy = "user")
    private List<UserMovie> userMovies;
    
    @OneToMany(mappedBy = "user")
    private List<UserTvShow> userTvShows;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public User(){}
    public User(String name, String firstName, String username){
        setName(name);
        setFirstName(firstName);
        setUsername(username);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public String getName(){return name;}
    public void setName(String name){
        DomainHelper.checkForStringValue("Name", name);
        this.name = name;
    }
    
    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){
        DomainHelper.checkForStringValue("First Name", firstName);
        this.name = firstName;
    }
    
    public String getUsername(){return username;}
    public void setUsername(String username){
        DomainHelper.checkForStringValue("Username", username);
        this.name = username;
    }
    
    public List<UserBook> getUserBooks() {
        return userBooks;
    }
    public void setUserBooks(List<UserBook> userBooks) {
        this.userBooks = userBooks;
    }

    public List<UserGame> getUserGames() {
        return userGames;
    }
    public void setUserGames(List<UserGame> userGames) {
        this.userGames = userGames;
    }

    public List<UserMovie> getUserMovies() {
        return userMovies;
    }
    public void setUserMovies(List<UserMovie> userMovies) {
        this.userMovies = userMovies;
    }

    public List<UserTvShow> getUserTvShows() {
        return userTvShows;
    }
    public void setUserTvShows(List<UserTvShow> userTvShows) {
        this.userTvShows = userTvShows;
    }
    
    //</editor-fold>

}
