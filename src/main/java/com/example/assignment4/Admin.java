package com.example.assignment4;

public class Admin {
    private Menu menu;
    private OrderQueue orderQueue;

    public Admin(Menu menu) {
        this.menu = menu;
        this.orderQueue = new OrderQueue();
    }

    public OrderQueue getOrderQueue() {
        return orderQueue;
    }
    public void setOrderQueue(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }
    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void addOrder(Order order) {
        orderQueue.addOrder(order);
    }

    public void removeOrder(Order order) {
        orderQueue.removeOrder(order);
    }

    public void updateOrderStatus(Order order, String status) {
        order.setStatus(status);
    }

    public void processRefund(Order order) {
        order.setStatus("Refunded");
    }

    public void generateDailyReport() {
        System.out.println("Daily Sales Report:");
        System.out.println("Total revenue: $" + orderQueue.getTotalRevenue());
        System.out.println("Number of orders: " + orderQueue.getOrders().size());
        System.out.println("Number of VIP orders: " + orderQueue.getVIPOrders().size());
        System.out.println("Number of pending orders: " + orderQueue.getPendingOrders().size());
        System.out.println("Number of completed orders: " + orderQueue.getCompletedOrders().size());
        System.out.println("Most popular item: " + orderQueue.getMostPopularItem());
    }
}
