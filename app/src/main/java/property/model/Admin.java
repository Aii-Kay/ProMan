package property.model;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }
    
    @Override
    String getRole() {
        return "Admin";
    }
}
