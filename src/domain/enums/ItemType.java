package domain.enums;

public enum ItemType {
    Book ("Book"), 
    Game ("Video Game"), 
    Movie ("Movie"), 
    TvShow ("Tv Show");
    
    private final String value;
    ItemType(String value){this.value = value;}
    public String value(){return value;}
    
    @Override    
    public String toString() {
        return value;
    }
    
}
