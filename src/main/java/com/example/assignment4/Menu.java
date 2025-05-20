package com.example.assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private List<FoodItem> menu;

    public Menu() {
        this.menu = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        menu.add(item);
    }

    public void removeItem(FoodItem item) {
        menu.remove(item);
    }

    public void displayMenu() {
        Map<String, List<FoodItem>> categoryItem = new HashMap<>();
        for (FoodItem item : menu) {
            if (!categoryItem.containsKey(item.getCategory())) {
                categoryItem.put(item.getCategory(), new ArrayList<>());
            }
            categoryItem.get(item.getCategory()).add(item);
        }
        for (Map.Entry<String, List<FoodItem>> entry : categoryItem.entrySet()) {
            System.out.println("Category: " + entry.getKey() + "\n");
            for (FoodItem item : entry.getValue()) {
                System.out.println(item.getName() + " - $" + item.getPrice() + " - " + item.getQuantity() + " left");
            }
            System.out.println("\n\n");
        }
    }

    public void filterByCategory(String category) {
        for (FoodItem item : menu) {
            if (item.getCategory().equals(category)) {
                System.out.println(item.getName() + " - $" + item.getPrice());
            }
        }
    }

    public void sortByPrice() {
        // Display the menu sorted by price (without actually changing the order of the menu)
        List<FoodItem> sortedMenu = new ArrayList<>(menu);
        sortedMenu.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        for (FoodItem item : sortedMenu) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    public void searchByName(String name) {
        boolean flag = false;
        for (FoodItem item : menu) {
            if (item.getName().contains(name)) {
                System.out.println(item.getName() + " - $" + item.getPrice());
                flag = true;
            }
        }
        if (menu.isEmpty()) {
            System.out.println("No items found");
        }
        if (!flag) {
            System.out.println("No items found with the name " + name);
        }
    }

    public List<FoodItem> getItems() {
        return menu;
    }
}
