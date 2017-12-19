package domain.item;

import domain.DomainHelper;
import domain.MyDate;
import domain.Person;
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
    
    public Book(){}
    public Book(String title, MyDate releaseDate, Person author) {this(title, releaseDate, author, false, null);}
    public Book(String title, MyDate releaseDate, Person author, boolean inCollection, Status state) {
        super(title, releaseDate, inCollection, state);
        setAuthor(author);
    }
    
    public Person getAuthor() {return author;}
    public void setAuthor(Person author) {
        DomainHelper.checkForValue("author", author);
        this.author = author;
    }
    
    @Override
    protected Status[] getAvailableStates() {
        return new Status[]{Status.ToDo, Status.Doing, Status.OnHold, Status.Done};
    }

}
