package domain.item;

import domain.DomainController;
import domain.DomainHelper;
import domain.MyDate;
import domain.entity.Person;
import domain.enums.ItemType;
import domain.enums.Status;
import domain.user.UserBook;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book extends Item<UserBook> implements Serializable{
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private Person author;    
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public Book(){super();}
    public Book(String title, MyDate releaseDate, Person author) {
        this();
        init(title, releaseDate, author);
    }
    public Book(String title, MyDate releaseDate, Person author, boolean inCollection, Status status){
        super(title, releaseDate, inCollection, status);
        setAuthor(author);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public Person getAuthor() {return author;}
    public void setAuthor(Person author) {
        DomainHelper.checkForValue("author", author);
        this.author = author;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @Override
    protected void createNewUserEntity(){
        userEntity = new UserBook(DomainController.getUser(), this);
    }
    
    /**
     * Sets all the values
     * @param title
     * @param releaseDate
     * @param author
     * @param inCollection
     * @param status 
     */
    private void init(String title, MyDate releaseDate, Person author){
        init(title, releaseDate);
        setAuthor(author);
    }
    
    /**
     * Changes the values
     * @param book 
     */
    public void editBook(Book book){
        if (book==null) return;
        init(book.getTitle(), book.getReleaseDate(), book.getAuthor());
    }
    
    @Override
    public ItemType getType() {
        return ItemType.Book;
    }
    
    //</editor-fold>

}
