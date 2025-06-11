package property.model; 

import java.io.*;
import java.util.*;

public class UserManager implements Authenticable {
    private List<User> users;
    private final String FILE_PATH = "users.csv";

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    @Override
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password)) {
                Session.setCurrentUser(user);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void logout() {
        Session.clear();
    }

    public void registerUser(User user) {
        users.add(user);
        saveUsers();
    }

    public void loadUsers() {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String role = parts[0];
                    String username = parts[1];
                    String password = parts[2];
                    if (role.equals("Admin")) {
                        users.add(new Admin(username, password));
                    } else if (role.equals("Buyer")) {
                        users.add(new Buyer(username, password));
                    } else {
                        System.err.println("Unknown role: " + role);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca data user: " + e.getMessage());
        }
    }

    public void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getRole() + "," + user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan data user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        return users;
    }

}
