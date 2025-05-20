package com.example.assignment4;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class PendingOrdersScreen {

    public static void showPendingOrdersScreen(Stage stage) {
        // Admin authentication
        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setHeaderText("Admin Authentication");
        passwordDialog.setContentText("Enter Admin Password:");
        String password = passwordDialog.showAndWait().orElse("");

        if (!password.equals("0000")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid password. Access denied.");
            alert.showAndWait();
            return;
        }

        // Retrieve pending orders using admin.getOrderQueue().getPendingOrders()
        List<Order> pendingOrders = ByteMeApp.getAdmin().getOrderQueue().getPendingOrders();


        // TableView for displaying orders
        TableView<Order> tableView = new TableView<>();
        TableColumn<Order, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        TableColumn<Order, String> customerCol = new TableColumn<>("Customer Name");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Order, String> itemsCol = new TableColumn<>("Items");
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("itemsSummary"));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(orderIdCol, customerCol, itemsCol, statusCol);
        tableView.getItems().addAll(pendingOrders);

        // Button to return to the main screen
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> stage.setScene(ByteMeGUI.createMainScene(stage)));

        // Layout for the screen
        VBox layout = new VBox(10, tableView, backButton);
        Scene ordersScene = new Scene(layout, 600, 400);

        stage.setScene(ordersScene);
        stage.setTitle("Pending Orders");
        stage.show();
    }
}