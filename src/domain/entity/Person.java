package domain.entity;

import domain.DomainHelper;
import domain.item.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A class for all people.
 * Could be authors, actors, writers...
 */
@Entity
public class Person extends IEntity implements Serializable{
    
    //<editor-fold defaultstate="collapsed" desc="variables">
    
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String firstName;
    
    @OneToMany(mappedBy = "author")
    private List<Book> books;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public Person(){books = new ArrayList<>();}
    public Person(String name, String firstName){
        this();
        init(name, firstName);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public String getName() {return name;}
    public void setName(String name) {
        DomainHelper.checkForStringValue("name", name);
        this.name = name;
    }

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {
        DomainHelper.checkForStringValue("first name", firstName);
        this.firstName = firstName;
    }
    
    public List<Book> getBooks() {return books;}
    public void setBooks(List<Book> books) {
        if (books==null) this.books = new ArrayList<>();
        else this.books = books;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    
    /**
     * sets all the values
     * @param name
     * @param firstName 
     */
    private void init(String name, String firstName){
        setName(name);
        setFirstName(firstName);
    }
    
    /**
     * Changes the values
     * @param person 
     */
    public void editPerson(Person person){
        if (person==null) return;
        init(person.getName(), person.getFirstName());
    }
    
    public void addBooks(Book... books){
        if (books==null) return;
        this.books.addAll(Arrays.asList(books));
    }
    public void removeBooks(Book... books){
        if (books==null) return;
        this.books.removeAll(Arrays.asList(books));
    }
    
    @Override
    public String toString() {
        return firstName.concat(" ").concat(name);
    }

    //</editor-fold>

}
