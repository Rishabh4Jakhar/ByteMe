package com.example.assignment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Order> orderHistory;
    private boolean isVIP;

    public Customer(String name) {
        this.name = name;
        this.orderHistory = new ArrayList<>();
        this.isVIP = false;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void cancelOrder(Order order) {
        ByteMeApp.getAdmin().removeOrder(order);
        orderHistory.remove(order);
    }

    public void placeOrder(Order order) {
        ByteMeApp.getAdmin().addOrder(order);
        orderHistory.add(order);
    }
    public String getName() {
        return name;
    }

    public void setVIP(boolean b) {
        isVIP = b;
    }

    public boolean hasOrdered(FoodItem item) {
        for (Order order : orderHistory) {
            if (order.getItems().containsKey(item)) {
                return true;
            }
        }
        return false;
    }
}
