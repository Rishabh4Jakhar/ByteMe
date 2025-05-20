package com.example.assignment4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int orderCount = 1;
    private int orderID;
    private Map<FoodItem, Integer> items;
    private String status;
    private boolean isVIP;
    private String specialRequest;
    private Customer customer;
    private String customerName;
    private String itemsSummary;

    public Order(Customer customer) {
        this.orderID = orderCount++;
        this.items = new HashMap<>();
        this.status = "Pending";
        this.customer = customer;
        this.customerName = customer.getName();
        this.isVIP = customer.isVIP();
        this.specialRequest = "";
        this.itemsSummary = "";
    }

    public int getOrderID() {
        return orderID;
    }
    public Map<FoodItem, Integer> getItems() {
        return items;
    }
    public String getStatus() {
        return status;
    }
    public String getItemsSummary() {
        return itemsSummary;
    }
    public String getCustomerName() {
        return customerName;
    }
    public boolean isVIP() {
        return isVIP;
    }

    public boolean checkForError (FoodItem testItem) {
        if (testItem.getQuantity() < 0) {
            System.out.println("Error: " + testItem.getName() + " is out of stock.");
            return false;
        }
        return true;
    }

    public void addItem(FoodItem item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
        // Update itemsSummary
        // For example, if items = {Burger=2, Fries=1}, then itemsSummary = "Burger x2, Fries x1"
        itemsSummary = "";
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            itemsSummary += entry.getKey().getName() + " x" + entry.getValue() + ", ";
        }
        itemsSummary = itemsSummary.substring(0, itemsSummary.length() - 2);
    }

    // Add item with quantity
    public void addItem(FoodItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
        // Update itemsSummary
        itemsSummary = "";
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            itemsSummary += entry.getKey().getName() + " x" + entry.getValue() + ", ";
        }
        itemsSummary = itemsSummary.substring(0, itemsSummary.length() - 2);
    }

    public void removeItem(FoodItem item) {
        items.remove(item);
        // Update itemsSummary
        itemsSummary = "";
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            itemsSummary += entry.getKey().getName() + " x" + entry.getValue() + ", ";
        }
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }
    public double getTotal() {
        double total = 0;
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice()*entry.getValue();
        }
        return total;
    }

    public void setSpecialRequest(String request) {
        specialRequest = request;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void getDetails() {
        // Display order details
        System.out.println("\n\n");
        System.out.println("Order ID: " + orderID);
        System.out.println("VIP: " + isVIP);
        System.out.println("Status: " + status);
        System.out.println("Customer: " + customerName);
        System.out.println("Items:");
        for (FoodItem item : items.keySet()) {
            System.out.println(item.getName() + " x" + items.get(item));
        }
        System.out.println("Total: $" + getTotal());
        System.out.println("Special Request: " + specialRequest);
        System.out.println("\n\n");
    }
}
