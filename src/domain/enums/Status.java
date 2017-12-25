package domain.enums;

/**
 * Currently available statuses
 * <ul>
 * <li>ToDo</li>
 * <li>Doing</li>
 * <li>OnHold</li>
 * <li>OnTrack</li>
 * <li>Done</li>
 * </ul>
 */
public enum Status {
    ToDo ("To Do"), 
    Doing ("Doing"), 
    OnHold ("On Hold"), 
    OnTrack ("On Track"), 
    Done ("Done");
    
    private final String value;
    Status(String value){this.value = value;}
    public String value(){return value;}
    
    @Override    
    public String toString() {
        return value;
    }
    
}
