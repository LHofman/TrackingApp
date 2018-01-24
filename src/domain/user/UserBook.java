package domain.user;

import domain.DomainHelper;
import domain.enums.Status;
import domain.item.Book;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Connects User and Book
 */
@Entity
public class UserBook extends UserItem implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "id")
    private Book entity;
    
    public UserBook(){}
    public UserBook(User user, Book book){this(user, book, false, Status.ToDo);}
    public UserBook(User user, Book book, boolean inCollection, Status status){
        super(user, inCollection, status);
        setBook(book);
    }
    
    public Book getBook(){return entity;}
    public void setBook(Book book){
        DomainHelper.checkForValue("Book", book);
        this.entity = book;
    }
    
    /**
     * @return the statuses available for this Item
     */
    @Override
    public Status[] getAvailableStatuses() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.Done};
    }

}
