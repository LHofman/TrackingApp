package exceptions;

public class InvalidArgumentException extends RuntimeException{
    
    private final String argument;
    private Object value;
    private String expected = "";
    
    public InvalidArgumentException(String argument) {
        this.argument = argument;
    }
    public InvalidArgumentException(String argument, Object value){
        this(argument);
        this.value = value;
    }
    public InvalidArgumentException(String argument, Object value, String expected){
        this(argument, value);
        this.expected = ", should be ".concat(expected);
    }

    @Override
    public String getMessage() {
        return String.format("invalid %s%s%s", 
                argument, 
                value==null ? "" : " (".concat(value.toString()).concat(")"), 
                expected);
    }
    
}