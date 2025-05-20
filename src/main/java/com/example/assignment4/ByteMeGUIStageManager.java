package com.example.assignment4;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ByteMeGUIStageManager {

    public static Scene createMainScene(Stage stage) {
        Button menuButton = new Button("Browse Menu");
        Button ordersButton = new Button("View Pending Orders");

        menuButton.setOnAction(e -> MenuScreen.showMenuScreen(stage));
        ordersButton.setOnAction(e -> PendingOrdersScreen.showPendingOrdersScreen(stage));

        VBox layout = new VBox(10, menuButton, ordersButton);
        return new Scene(layout, 300, 200);
    }
}