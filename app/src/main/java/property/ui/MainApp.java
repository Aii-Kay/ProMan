package property.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import property.model.*;

public class MainApp extends Application {

    private UserManager userManager = new UserManager();
    private PropertyManager propertyManager = new PropertyManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Manajemen Properti");
        showMainMenu(primaryStage);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ESC to exit full screen");
        primaryStage.show();
    }

    public void showMainMenu(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPrefWidth(400);

        Label title = new Label("===== SELAMAT DATANG =====");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Button exitButton = new Button("Keluar");

        loginButton.setOnAction(e -> showLoginPage(primaryStage));
        registerButton.setOnAction(e -> showRegisterPage(primaryStage));
        exitButton.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(title, loginButton, registerButton, exitButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showLoginPage(Stage primaryStage) {
        LoginPage loginPage = new LoginPage(primaryStage, userManager, propertyManager);
        loginPage.showLoginPage();
    }

    private void showRegisterPage(Stage primaryStage) {
        RegisterPage registerPage = new RegisterPage(primaryStage, userManager);
        registerPage.showRegisterPage();
    }

    private void showAdminMenu(Stage primaryStage) {
        AdminMenu adminMenu = new AdminMenu(primaryStage, propertyManager);
        adminMenu.showAdminMenu();
    }

    private void showBuyerMenu(Stage primaryStage) {
        BuyerMenu buyerMenu = new BuyerMenu(primaryStage, propertyManager);
        buyerMenu.showBuyerMenu();
    }
}
