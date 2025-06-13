package property.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import property.model.*;

public class AdminMenu {

    private Stage primaryStage;
    private PropertyManager propertyManager;

    public AdminMenu(Stage primaryStage, PropertyManager propertyManager) {
        this.primaryStage = primaryStage;
        this.propertyManager = propertyManager;
    }

    public void showAdminMenu() {
        VBox layout = new VBox(15);
        layout.setPrefWidth(350);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Welcome, Admin!");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;");

        Button addPropertyButton = createButton("Add Property");
        Button viewPropertiesButton = createButton("View All Properties");
        Button logoutButton = createButton("Logout");

        addPropertyButton.setOnAction(e -> showAddPropertyPage());
        viewPropertiesButton.setOnAction(e -> showAllProperties());
        logoutButton.setOnAction(e -> logout());

        VBox buttonLayout = new VBox(10, addPropertyButton, viewPropertiesButton, logoutButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        layout.getChildren().addAll(title, buttonLayout);

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

    private void showAddPropertyPage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Property");
        alert.setHeaderText(null);
        alert.setContentText("Feature to add a property will be implemented here.");
        alert.showAndWait();
    }

    private void showAllProperties() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View Properties");
        alert.setHeaderText(null);
        alert.setContentText("Feature to view all properties will be implemented here.");
        alert.showAndWait();
    }

    private void logout() {
        Session.clear();
        MainApp mainApp = new MainApp();
        mainApp.showMainMenu(primaryStage);
    }
}