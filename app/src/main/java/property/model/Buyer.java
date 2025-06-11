package property.model; 

public class Buyer extends User {
    public Buyer(String username, String password) {
        super(username, password);
    }
    
    @Override
    String getRole() {
        return "Buyer";
    }
    
}
