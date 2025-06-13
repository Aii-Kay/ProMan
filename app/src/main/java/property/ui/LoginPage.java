package property.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import property.model.*;

public class LoginPage {

    private Stage primaryStage;
    private UserManager userManager;
    private PropertyManager propertyManager;

    public LoginPage(Stage primaryStage, UserManager userManager, PropertyManager propertyManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager;
        this.propertyManager = propertyManager;
    }

    public void showLoginPage() {
        VBox layout = new VBox(20);
        layout.setPrefWidth(350);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-background-radius: 5; -fx-padding: 10;");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-background-radius: 5; -fx-padding: 10;");
        
        Button loginButton = createButton("Login");
        Button backButton = createButton("Back");

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        backButton.setOnAction(e -> goBackToMainMenu());

        VBox buttonLayout = new VBox(10, loginButton, backButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        layout.getChildren().addAll(title, usernameField, passwordField, buttonLayout);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ESC to exit full screen");
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
        button.setMinWidth(200);

        // Menambahkan animasi hover
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        button.setOnMouseEntered(e -> {
            scaleTransition.play(); 
            button.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: #45a049; -fx-text-fill: white; -fx-background-radius: 5;");
        });
        button.setOnMouseExited(e -> {
            scaleTransition.setRate(-1); 
            button.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
        });

        DropShadow shadow = new DropShadow();
        shadow.setColor(javafx.scene.paint.Color.GRAY);
        shadow.setRadius(10);
        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));

        return button;
    }

    private void handleLogin(String username, String password) {
        if (userManager.login(username, password)) {
            User currentUser = Session.getCurrentUser();
            if (currentUser instanceof Admin) {
                showAdminMenu();
            } else if (currentUser instanceof Buyer) {
                showBuyerMenu();
            }
        } else {
            showError("Login failed: Invalid credentials.");
        }
    }

    private void showAdminMenu() {
        AdminMenu adminMenu = new AdminMenu(primaryStage, propertyManager);
        adminMenu.showAdminMenu();
    }

    private void showBuyerMenu() {
        BuyerMenu buyerMenu = new BuyerMenu(primaryStage, propertyManager);
        buyerMenu.showBuyerMenu();
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
}