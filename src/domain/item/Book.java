package domain.item;

import domain.DomainHelper;
import domain.MyDate;
import domain.Person;
import domain.enums.State;

public class Book extends Item{
    
    private Person author;    
    
    public Book(String title, MyDate releaseDate, Person author) {this(title, releaseDate, author, false, null);}
    public Book(String title, MyDate releaseDate, Person author, boolean inCollection, State state) {
        super(title, releaseDate, inCollection, state);
        setAuthor(author);
    }
    
    public Person getAuthor() {return author;}
    public final void setAuthor(Person author) {
        DomainHelper.checkForValue("author", author);
        this.author = author;
    }
    
    @Override
    protected State[] getAvailableStates() {
        return new State[]{State.ToDo, State.Doing, State.OnHold, State.Done};
    }

}
