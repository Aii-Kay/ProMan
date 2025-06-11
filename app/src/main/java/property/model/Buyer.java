package property.model; // selesai

public class Buyer extends User {
    public Buyer(String username, String password) {
        super(username, password);
    }
    
    @Override
    String getRole() {
        return "Buyer";
    }
    
}
