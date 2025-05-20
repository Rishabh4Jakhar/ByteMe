package com.example.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderManagerTest {
    private OrderManager orderManager;
    private Menu menu;

    @BeforeEach
    void setUp() {
        // Adding some stock items
        menu = new Menu();
        menu.addItem(new FoodItem("Burger", 5.0, "Fast Food", true, 2));
        menu.addItem(new FoodItem("Pizza", 10.0, "Fast Food", true, 3));
        menu.addItem(new FoodItem("Pasta", 8.0, "Italian", true, 1));
    }

    @Test
    public void testInStock() {
        orderManager = new OrderManager();
        Order order = new Order(new Customer("Alice"));
        FoodItem burger = ByteMeApp.findMenuItemByName(menu, "Burger");
        order.addItem(burger, 2);
        burger.decreaseQuantity(2);
        String result = orderManager.saveOrder(order, burger);
        assertEquals("Order placed successfully!", result, "Expected order to be placed successfully");
    }

    @Test
    public void testOutOfStock() {
        orderManager = new OrderManager();
        Order order = new Order(new Customer("Bob"));
        FoodItem pasta = ByteMeApp.findMenuItemByName(menu, "Pasta");
        order.addItem(pasta, 2);
        pasta.decreaseQuantity(2);
        String result = orderManager.saveOrder(order, pasta);
        assertEquals("Error: Pasta is out of stock.", result, "Expected Pastax to be out of stock");
    }

}