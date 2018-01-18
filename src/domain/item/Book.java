package domain.item;

import domain.DomainHelper;
import domain.MyDate;
import domain.entity.Person;
import domain.enums.ItemType;
import domain.enums.Status;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book extends Item implements Serializable{
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private Person author;    
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public Book(){}
    public Book(String title, MyDate releaseDate, Person author) {this(title, releaseDate, author, false, null);}
    public Book(String title, MyDate releaseDate, Person author, boolean inCollection, Status status) {
        init(title, releaseDate, author, inCollection, status);
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
    
    /**
     * Sets all the values
     * @param title
     * @param releaseDate
     * @param author
     * @param inCollection
     * @param status 
     */
    private void init(String title, MyDate releaseDate, Person author, boolean inCollection, Status status){
        init(title, releaseDate, inCollection, status);
        setAuthor(author);
    }
    
    /**
     * Changes the values
     * @param book 
     */
    public void editBook(Book book){
        if (book==null) return;
        init(book.getTitle(), book.getReleaseDate(), book.getAuthor(), book.isInCollection(), book.getStatus());
    }
    
    /**
     * @return the statuses available for this Item
     */
    @Override
    public Status[] getAvailableStatuses() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.Done};
    }

    @Override
    public ItemType getType() {
        return ItemType.Book;
    }
    
    //</editor-fold>

}
