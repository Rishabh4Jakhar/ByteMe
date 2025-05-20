package com.example.assignment4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static final String ORDERS_FILE = "orders.ser";

    // Save a single order to the serialized file
    public String saveOrder(Order order, FoodItem testItem) {
        if (order.checkForError(testItem)) {
            List<Order> orders = loadAllOrders(); // Load existing orders
            orders.add(order); // Add the new order
            saveAllOrders(orders); // Save back to the file
            return "Order placed successfully!";
        } else {
            return "Error: " + testItem.getName() + " is out of stock.";
        }
    }

    public void saveOrder(Order order) {
        //order.checkForError();
        List<Order> orders = loadAllOrders(); // Load existing orders
        orders.add(order); // Add the new order
        saveAllOrders(orders); // Save back to the file
    }


    // Save all orders to the serialized file
    public void saveAllOrders(List<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }

    // Load all orders from the serialized file
    @SuppressWarnings("unchecked")
    public List<Order> loadAllOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            return (List<Order>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous orders found. Starting fresh.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Load orders for a specific customer
    public List<Order> loadOrdersForCustomer(String customerName) {
        List<Order> allOrders = loadAllOrders(); // Load all orders
        List<Order> customerOrders = new ArrayList<>();

        for (Order order : allOrders) {
            if (order.getCustomerName().equals(customerName)) {
                customerOrders.add(order); // Filter orders by customer name
            }
        }

        return customerOrders;
    }
}
