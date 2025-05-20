package com.example.assignment4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ByteMeGUI extends Application {
    private static Admin admin;

    public static void startGUI(Admin adminObject) {
        admin = adminObject;
        Application.launch(ByteMeGUI.class); // Launch JavaFX
    }


    public static Scene createMainScene(Stage stage) {
        Button menuButton = new Button("Browse Menu");
        Button ordersButton = new Button("View Pending Orders");
        menuButton.setOnAction(e -> MenuScreen.showMenuScreen(stage));
        ordersButton.setOnAction(e -> PendingOrdersScreen.showPendingOrdersScreen(stage));
        VBox layout = new VBox(10, menuButton, ordersButton);
        return new Scene(layout, 300, 200);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Byte Me - GUI");

        // Main GUI layout
        VBox layout = new VBox(10);

        Button menuButton = new Button("Browse Menu");
        Button ordersButton = new Button("View Pending Orders");

        // Add actions for the buttons
        menuButton.setOnAction(e -> MenuScreen.showMenuScreen(primaryStage));
        ordersButton.setOnAction(e -> PendingOrdersScreen.showPendingOrdersScreen(primaryStage));
        layout.getChildren().addAll(menuButton, ordersButton);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}