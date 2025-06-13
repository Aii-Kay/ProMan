package property.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import property.model.*;

import java.util.List;

public class BuyerMenu {

    private Stage primaryStage;
    private PropertyManager propertyManager;

    public BuyerMenu(Stage primaryStage, PropertyManager propertyManager) {
        this.primaryStage = primaryStage;
        this.propertyManager = propertyManager;
    }

    public void showBuyerMenu() {
        VBox layout = new VBox(15);
        layout.setPrefWidth(350);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Welcome, Buyer!");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;");

        Button viewPropertiesButton = createButton("View Properties");
        Button buyPropertyButton = createButton("Buy Property");
        Button logoutButton = createButton("Logout");

        viewPropertiesButton.setOnAction(e -> showAllAvailableProperties());
        buyPropertyButton.setOnAction(e -> showBuyPropertyPage());
        logoutButton.setOnAction(e -> logout());

        VBox buttonLayout = new VBox(10, viewPropertiesButton, buyPropertyButton, logoutButton);
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

    private void showAllAvailableProperties() {
        List<Property> availableProperties = propertyManager.getAvailableProperties();

        if (availableProperties.isEmpty()) {
            showError("No available properties at the moment.");
            return;
        }

        VBox layout = new VBox(15);
        layout.setPrefWidth(350);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Available Properties");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;");

        for (Property property : availableProperties) {
            Label propertyLabel = new Label("ID: " + property.getId() +
                    " | Name: " + property.getNama() +
                    " | Type: " + property.getType() +
                    " | Price: " + property.getHarga() +
                    " | Status: " + (property.getStatus() == PropertyStatus.TERJUAL ? "SOLD" : "AVAILABLE"));
            propertyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
            layout.getChildren().add(propertyLabel);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showBuyerMenu());
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: #FF5722; -fx-text-fill: white; -fx-background-radius: 5;");

        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ESC to exit full screen");
        primaryStage.show();
    }

    private void showBuyPropertyPage() {
        List<Property> availableProperties = propertyManager.getAvailableProperties();

        if (availableProperties.isEmpty()) {
            showError("No properties available to buy.");
            return;
        }

        VBox layout = new VBox(15);
        layout.setPrefWidth(350);
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Select Property to Buy");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;");

        for (Property property : availableProperties) {
            Label propertyLabel = new Label("ID: " + property.getId() +
                    " | Name: " + property.getNama() +
                    " | Type: " + property.getType() +
                    " | Price: " + property.getHarga());
            propertyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
            Button buyButton = new Button("Buy Property");
            buyButton.setOnAction(e -> buyProperty(property.getId()));
            layout.getChildren().addAll(propertyLabel, buyButton);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showBuyerMenu());
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: #FF5722; -fx-text-fill: white; -fx-background-radius: 5;");

        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ESC to exit full screen");
        primaryStage.show();
    }

    private void buyProperty(int propertyId) {
        Property property = propertyManager.cariById(propertyId);

        if (property == null || property.getStatus() == PropertyStatus.TERJUAL) {
            showError("Property is already sold or does not exist.");
            return;
        }

        showPaymentOptions(propertyId);
    }

    private void showPaymentOptions(int propertyId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Payment Method");
        alert.setHeaderText("Choose payment method for property ID " + propertyId);
        alert.setContentText("1. Bank Transfer\n2. Credit Card");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                showSuccess("Property purchased successfully. Status updated to SOLD.");
                propertyManager.markAsSold(propertyId);
            } else {
                showError("Payment failed or cancelled.");
            }
        });
    }

    private void logout() {
        Session.clear();
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