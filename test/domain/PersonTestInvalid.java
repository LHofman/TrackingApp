package domain;

import exceptions.InvalidArgumentException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Invalid test cases for {@link domain.Item.Item}
 */
@RunWith(Parameterized.class)
public class PersonTestInvalid {
    
    private Person person;
    private final String name, firstName;
    
    @Parameters
    public static Collection<Object[]> getTestParameters(){
        return Arrays.asList(new Object[][]{
            {null, "Lennert"},
            {"", "Lennert"},
            {" ", "Lennert"},
            {"Hofman", null},
            {"Hofman", ""},
            {"Hofman", "   "}
        });
    }
    
    public PersonTestInvalid(String name, String firstName){
        this.name = name;
        this.firstName = firstName;
    }
    
    @Test(expected = InvalidArgumentException.class)
    public void testItemValid(){
        person = new Person(name, firstName);
    }
    
}
