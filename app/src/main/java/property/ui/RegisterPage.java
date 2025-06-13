package property.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import property.model.*;

public class RegisterPage {

    private Stage primaryStage;
    private UserManager userManager;

    public RegisterPage(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager;
    }

    public void showRegisterPage() {
        VBox layout = new VBox(10);
        layout.setPrefWidth(300);

        Label title = new Label("Register");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Buyer");
        roleComboBox.setPromptText("Select Role");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        registerButton.setOnAction(e -> handleRegister(roleComboBox.getValue(), usernameField.getText(), passwordField.getText()));
        backButton.setOnAction(e -> goBackToMainMenu());

        layout.getChildren().addAll(title, usernameField, passwordField, roleComboBox, registerButton, backButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ESC to exit full screen");
    }

    private void handleRegister(String role, String username, String password) {
        if (role == null || username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        if (role.equalsIgnoreCase("Admin")) {
            userManager.registerUser(new Admin(username, password));
        } else if (role.equalsIgnoreCase("Buyer")) {
            userManager.registerUser(new Buyer(username, password));
        }

        showSuccess("Registration successful! Please login.");
        goBackToMainMenu();
    }

    private void goBackToMainMenu() {
        MainApp mainApp = new MainApp();
        mainApp.showMainMenu(primaryStage);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
