package property.model; 

public interface Authenticable {
    boolean login (String username, String password);
    void logout();
}
