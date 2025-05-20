package com.example.assignment4;

import java.util.*;

public class OrderQueue {
    private TreeSet<Order> orders;

    public OrderQueue() {
        this.orders = new TreeSet<>((o1, o2) -> {
            if (o1.isVIP() && !o2.isVIP()) {
                return -1;
            } else if (!o1.isVIP() && o2.isVIP()) {
                return 1;
            } else {
                return Integer.compare(o1.getOrderID(), o2.getOrderID());
            }
        });
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public void initOrders(List<Order> orders) {
        for (Order order : orders) {
            if (Objects.equals(order.getStatus(), "Pending")) {
                this.orders.add(order);
            }
        }
    }
    public Order getNextOrder() {
        Order firstOrder = orders.isEmpty() ? null : orders.first();
        if (firstOrder != null) {
            orders.remove(firstOrder);
        }
        return firstOrder;
    }

    public TreeSet<Order> getOrders() {
        return orders;
    }

    public List<Order> getVIPOrders() {
        List<Order> vipOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.isVIP()) {
                vipOrders.add(order);
            }
        }
        return vipOrders;
    }
    public Order getOrderByID(int orderID) {
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;
    }
    public void displayPendingOrders() {
        for (Order order : orders) {
            if (order.getStatus().equals("Pending")) {
                System.out.println("Order ID: " + order.getOrderID());
                System.out.println("VIP: " + order.isVIP());
                System.out.println("Status: " + order.getStatus());
                System.out.println("Customer: " + order.getCustomer().getName());
                System.out.println("Items:");
                for (FoodItem item : order.getItems().keySet()) {
                    System.out.println(item.getName() + " x" + order.getItems().get(item));
                }
                System.out.println("Total: $" + order.getTotal());
                System.out.println();
            }
        }
    }
    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Pending")) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    public List<Order> getCompletedOrders() {
        List<Order> completedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("Completed")) {
                completedOrders.add(order);
            }
        }
        return completedOrders;
    }

    public double getTotalRevenue() {
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getTotal();
        }
        return totalRevenue;
    }

    public String getMostPopularItem() {
        Map<String, Integer> itemCount = new HashMap<>();
        for (Order order : orders) {
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                itemCount.put(entry.getKey().getName(), itemCount.getOrDefault(entry.getKey().getName(), 0) + entry.getValue());
            }
        }

        String mostPopularItem = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : itemCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopularItem = entry.getKey();
            }
        }

        return mostPopularItem + " (" + maxCount + " units ordered)";
    }

    public void displayPriorityOrders() {
        System.out.println("Priority Orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Customer: " + order.getCustomer().getName());
            System.out.println("VIP: " + order.isVIP());
            System.out.println();
        }
    }

    public void displaySpecialRequests() {
        System.out.println("Orders with Special Requests:");
        for (Order order : orders) {
            if (!order.getSpecialRequest().isEmpty()) {
                System.out.println("Order ID: " + order.getOrderID());
                System.out.println("Customer: " + order.getCustomer().getName());
                System.out.println("Special Request: " + order.getSpecialRequest());
                System.out.println();
            }
        }
    }
}
