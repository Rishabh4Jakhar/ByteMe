package com.example.assignment4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.Comparator;
import java.util.List;

public class MenuScreen {

    public static void showMenuScreen(Stage stage) {
        List<FoodItem> menuItems = ByteMeApp.getAdmin().getMenu().getItems();
        // TableView for displaying menu items
        TableView<FoodItem> tableView = new TableView<>();
        TableColumn<FoodItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<FoodItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<FoodItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<FoodItem, Boolean> availabilityCol = new TableColumn<>("Available");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        tableView.getColumns().addAll(nameCol, priceCol, categoryCol, availabilityCol);
        //tableView.getItems().addAll(menuItems);
        ObservableList<FoodItem> items = FXCollections.observableArrayList(ByteMeApp.getAdmin().getMenu().getItems());
        tableView.setItems(items);

        // Button to sort the menu by price
        Button sortButton = new Button("Sort by Price");
        sortButton.setOnAction(e -> {
            items.sort(Comparator.comparingDouble(FoodItem::getPrice));
            tableView.setItems(FXCollections.observableArrayList(items));
        });

        // Button to reset the filter and sorting
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> tableView.setItems(FXCollections.observableArrayList(ByteMeApp.getAdmin().getMenu().getItems())));


        // Button to return to the main screen
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> stage.setScene(ByteMeGUI.createMainScene(stage)));

        // Layout for the screen
        VBox layout = new VBox(10, tableView, sortButton, resetButton, backButton);
        Scene menuScene = new Scene(layout, 600, 400);

        stage.setScene(menuScene);
        stage.setTitle("Browse Menu");
        stage.show();
    }
}