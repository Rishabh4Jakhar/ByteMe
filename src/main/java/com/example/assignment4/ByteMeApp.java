package com.example.assignment4;

import javafx.application.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ByteMeApp {
    private static Menu menu = new Menu();
    private static Scanner scanner = new Scanner(System.in);
    private static Admin admin = new Admin(menu);
    private static final String ADMIN_PASSWORD = "0000"; // Fixed admin password
    private static final Map<String, String> customers_data = new HashMap<>(); // Store customer ID and password
    private static Map<String, Customer> customers = new HashMap<>();

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        OrderManager orderManager = new OrderManager();
        admin.setOrderQueue(new OrderQueue());
        initializeMenu();
        System.out.println("Loading previous orders...");
        List<Order> previousOrders = orderManager.loadAllOrders();
        admin.getOrderQueue().initOrders(previousOrders);
        System.out.println("Previous orders loaded successfully.");
        while (true) {
            System.out.println("Welcome to ByteMe!\n\n");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. GUI");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            if (userChoice == 1) {
                if (adminLogin(scanner)) {
                    showAdminMenu(admin);
                } else {
                    System.out.println("Invalid password. Access denied.");
                }
                //showAdminMenu(admin);
            } else if (userChoice == 2) {
                String name = customerLogin(scanner, userManager);
                if (name != null) {
                    //Customer customer = new Customer(customerName);
                    Customer customer = customers.computeIfAbsent(name, Customer::new);
                customerMenu(customer, orderManager);
                }
                //System.out.print("Enter your name: ");
                //String name = scanner.nextLine();

            } else if (userChoice == 3) {
                System.out.println("Launching GUI...");
                ByteMeGUI.startGUI(admin);
            } else if (userChoice == 4) {
                System.out.println("Exiting...");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static boolean adminLogin(Scanner scanner) {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        return ADMIN_PASSWORD.equals(password);
    }

    private static String customerLogin(Scanner scanner, UserManager userManager) {
        System.out.print("Enter your name (ID): ");
        String username = scanner.nextLine();
        // Check if the user exists
        if (userManager.userExists(username)) {
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            if (userManager.authenticateUser(username, password)) {
                System.out.println("Login successful! Welcome, " + username);
                // Placeholder for customer functionalities
                System.out.println("Accessing customer interface...");
                return username;
            } else {
                System.out.println("Invalid password. Please try again.");
                return null;
            }
        } else {
            System.out.print("User not found. Do you want to register? (yes/no): ");
            String response = scanner.nextLine();
            if ("yes".equalsIgnoreCase(response)) {
                System.out.print("Set a password: ");
                String password = scanner.nextLine();
                if (userManager.registerUser(username, password)) {
                    System.out.println("Registration successful! Welcome, " + username);
                    return username;
                } else {
                    System.out.println("Registration failed. User already exists.");
                    return null;
                }
            } else {
                System.out.println("Returning to main menu.");
                return null;
            }
        }
    }

    private static void customerMenu(Customer customer, OrderManager orderManager) {
        //OrderManager orderManager = new OrderManager();
        while (true) {
            if (customer.isVIP()) {
                System.out.println("Welcome " + customer.getName() + "!\n**VIP Member**\n");
            } else {
                System.out.println("Welcome " + customer.getName() + "!\n\n");
            }
            System.out.println("1. Browse Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Track Order");
            System.out.println("4. Item Review");
            System.out.println("5. Change VIP Status");
            System.out.println("6. View Order History");
            System.out.println("7. Cancel Order");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int customerChoice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            if (customerChoice == 1) {
                browseMenu();
            } else if (customerChoice == 2) {
                placeOrder(customer, orderManager);
            } else if (customerChoice == 3) {
                trackOrder(customer);
            } else if (customerChoice == 4) {
                // Ask user if they want to view review or add review
                System.out.println("1. View Review");
                System.out.println("2. Add Review");
                System.out.print("Enter your choice: ");
                int reviewChoice = scanner.nextInt();
                scanner.nextLine();
                if (reviewChoice == 1) {
                    System.out.print("Enter item name to view reviews: ");
                    String itemName = scanner.nextLine();
                    FoodItem item = findMenuItemByName(itemName);
                    if (item != null) {
                        System.out.println("Rating: " + item.getRating() + " / 5");
                        System.out.println("Reviews:");
                        // If no reviews, print "No reviews"
                        if (item.getReviews().isEmpty()) {
                            System.out.println("No reviews");
                        }
                        for (String review : item.getReviews()) {
                            System.out.println("-> " + review);
                        }
                        System.out.println("\n\n");
                    } else {
                        System.out.println("Item not found.");
                    }
                } else if (reviewChoice == 2) {
                    System.out.print("Enter item name to review: ");
                    String itemName = scanner.nextLine();
                    FoodItem item = findMenuItemByName(itemName);
                    if (item != null) {
                        System.out.println("Rating: " + item.getRating() + " / 5");
                        System.out.println("Reviews:");
                        if (item.getReviews().isEmpty()) {
                            System.out.println("No reviews");
                        }
                        for (String review : item.getReviews()) {
                            System.out.println("-> " + review);
                        }
                        if (customer.hasOrdered(item)) {
                            System.out.print("Enter your rating (1-5): ");
                            int rating = scanner.nextInt();
                            scanner.nextLine();
                            item.addRating(rating);
                            System.out.print("Enter your review: ");
                            String review = scanner.nextLine();
                            item.addReview(review, customer);
                            System.out.println("Rating and review added successfully.");
                        } else {
                            System.out.println("You have not ordered this item.");
                        }
                        System.out.println("\n\n");
                    } else {
                        System.out.println("Item not found.");
                    }
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else if (customerChoice == 5) {
                customer.setVIP(!customer.isVIP());
                System.out.println("VIP status changed successfully to " + customer.isVIP());
            } else if (customerChoice == 6) {
                List<Order> orders = orderManager.loadOrdersForCustomer(customer.getName());
                if (orders.isEmpty()) {
                    System.out.println("No orders found for " + customer.getName());
                } else {
                    System.out.println("Order History for " + customer.getName() + ":");
                    for (Order order : orders) {
                        order.getDetails();
                    }
                }
                //viewOrderHistory(customer);
            } else if (customerChoice == 7) {
                cancelOrder(customer);
            } else if (customerChoice == 8) {
                System.out.println("Exiting...");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void viewOrderHistory(Customer customer) {
        List<Order> orderHistory = customer.getOrderHistory();
        if (orderHistory.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orderHistory) {
                System.out.println("Order ID: " + order.getOrderID());
                System.out.println("Status: " + order.getStatus());
                System.out.println("Items:");
                for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                    System.out.println(entry.getKey().getName() + " x " + entry.getValue() + " - $" + entry.getKey().getPrice() * entry.getValue());
                }
                System.out.println("Total: $" + order.getTotal());
                System.out.println();
            }
        }
    }

    private static void cancelOrder(Customer customer) {
        System.out.print("Enter order ID to cancel: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        Order order = admin.getOrderQueue().getOrderByID(orderId);
        if (order != null && order.getCustomer().equals(customer) && order.getStatus().equals("Pending")) {
            customer.cancelOrder(order);
            System.out.println("Order canceled successfully.");
        } else {
            System.out.println("Order not found or cannot be canceled. Status: " + order.getStatus());
        }
    }

    private static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("Welcome Admin!\n\n");
            System.out.println("1. Manage Menu");
            System.out.println("2. Manage Orders");
            System.out.println("3. Generate Daily Report");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            if (adminChoice == 1) {
                manageMenu();
            } else if (adminChoice == 2) {
                manageOrders(admin);
            } else if (adminChoice == 3) {
                admin.generateDailyReport();
            } else if (adminChoice == 4) {
                System.out.println("Exiting...");
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }

    }

    private static void manageOrders(Admin admin) {
        while (true) {
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Process Refund");
            System.out.println("4. Handle Special Requests");
            System.out.println("5. See Pending Orders in Priority Order");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int orderChoice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            if (orderChoice == 1) {
                admin.getOrderQueue().displayPendingOrders();
            } else if (orderChoice == 2) {
                System.out.print("Enter order ID: ");
                int orderId = scanner.nextInt();
                scanner.nextLine();
                Order order = admin.getOrderQueue().getOrderByID(orderId);
                if (order != null) {
                    System.out.print("Enter new status: ");
                    String status = scanner.nextLine();
                    admin.updateOrderStatus(order, status);
                    System.out.println("Order status updated successfully.");
                } else {
                    System.out.println("Order not found.");
                }
            } else if (orderChoice == 3) {
                System.out.print("Enter order ID: ");
                int orderId = scanner.nextInt();
                scanner.nextLine();
                Order order = admin.getOrderQueue().getOrderByID(orderId);
                if (order != null) {
                    admin.processRefund(order);
                    System.out.println("Refund processed successfully.");
                } else {
                    System.out.println("Order not found.");
                }
            } else if (orderChoice == 4) {
                admin.getOrderQueue().displaySpecialRequests();
            } else if (orderChoice == 5) {
                admin.getOrderQueue().displayPriorityOrders();
            } else if (orderChoice == 6) {
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    // src/ByteMeApp.java
    private static void initializeMenu() {
        menu.addItem(new FoodItem("Burger", 10, "Fast Food", true, 10));
        menu.addItem(new FoodItem("Pizza", 30, "Fast Food", true, 10));
        menu.addItem(new FoodItem("Pasta", 15, "Fast Food", true, 10));
        menu.addItem(new FoodItem("Samosa", 10, "Snacks", true, 10));
        menu.addItem(new FoodItem("Bread Pakoda", 15, "Snacks", true, 10));
        menu.addItem(new FoodItem("Patty", 5, "Snacks", true, 10));
        menu.addItem(new FoodItem("Coke", 2, "Beverages", true, 10));
        menu.addItem(new FoodItem("Pepsi", 2, "Beverages", true, 10));
        menu.addItem(new FoodItem("Sprite", 2, "Beverages", true, 10));
    }

    private static void browseMenu() {
        System.out.println("Menu:");
        menu.displayMenu();
        while (true) {
            System.out.println("Options:");
            System.out.println("1. Search by category");
            System.out.println("2. Sort by price");
            System.out.println("3. Search for an item/keyword");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            if (choice == 1) {
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                menu.filterByCategory(category);
            } else if (choice == 2) {
                menu.sortByPrice();
            } else if (choice == 3) {
                System.out.print("Enter item name: ");
                String itemName = scanner.nextLine();
                menu.searchByName(itemName);
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void placeOrder(Customer customer, OrderManager orderManager) {
        Order order = new Order(customer);
        while (true) {
            System.out.println("Enter item name to add to order (or 'done' to finish, 'edit' to edit quantity, 'remove' to remove item, 'show' to see current order): ");
            String itemName = scanner.nextLine();
            double total = 0;
            if (itemName.equalsIgnoreCase("done")) {
                if (order.getItems().isEmpty()) {
                    System.out.println("Order Cancelled as no items were added");
                    return;
                }
                break;
            } else if (itemName.equalsIgnoreCase("remove")) {
                System.out.print("Enter item name to remove: ");
                String itemToRemove = scanner.nextLine();
                FoodItem item = findMenuItemByName(itemToRemove);
                if (item != null && order.getItems().containsKey(item)) {
                    for (int i = 0; i < order.getItems().get(item); i++) {
                        item.increaseQuantity();
                    }
                    System.out.println(itemToRemove + " ( " + order.getItems().get(item) + " units) removed from order.");
                    order.removeItem(item);
                    continue;
                } else {
                    System.out.println("Item not found.");
                    continue;
                }
            } else if (itemName.equalsIgnoreCase("cancel")) {
                System.out.println("Order cancelled.");
                return;
            } else if (itemName.equalsIgnoreCase("edit")) {
                System.out.print("Enter item name to edit: ");
                String itemToEdit = scanner.nextLine();
                FoodItem item = findMenuItemByName(itemToEdit);
                if (item != null && order.getItems().containsKey(item)) {
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < order.getItems().get(item); i++) {
                        item.increaseQuantity();
                    }
                    order.getItems().put(item, newQuantity);
                    for (int i = 0; i < newQuantity; i++) {
                        item.decreaseQuantity();
                    }
                    System.out.println(itemToEdit + " quantity updated to " + newQuantity);
                    continue;
                } else {
                    System.out.println("Item not found.");
                    continue;
                }
            } else if (itemName.equalsIgnoreCase("show")) {
                System.out.println("Current Order:");
                if (order.getItems().isEmpty()) {
                    System.out.println("No items in order.");
                    continue;
                }
                for (FoodItem orderItem : order.getItems().keySet()) {
                    System.out.println(orderItem.getName() + " x " + order.getItems().get(orderItem) + " - $" + orderItem.getPrice()*order.getItems().get(orderItem));
                }
                continue;
            }
            FoodItem item = findMenuItemByName(itemName);
            if (item != null) {
                if (!item.isAvailable()) {
                    System.out.println("Item not available.");
                    continue;
                }
                order.addItem(item);
                item.decreaseQuantity();
                System.out.println(itemName + " added to order.");
                System.out.println("Want to add a special request? (Y/N)");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.print("Enter special request: ");
                    String request = scanner.nextLine();
                    order.setSpecialRequest(request);
                }
                System.out.println("Current Order:");
                for (FoodItem orderItem : order.getItems().keySet()) {
                    System.out.println(orderItem.getName() + " x " + order.getItems().get(orderItem) + " - $" + orderItem.getPrice()*order.getItems().get(orderItem));
                }
                total += item.getPrice();
            } else {
                System.out.println("Item not found.");
            }
        }
        customer.placeOrder(order);
        String itemsList = order.getItemsSummary();
        orderManager.saveOrder(order);
        System.out.println("Order placed successfully. Your order ID is " + order.getOrderID());
    }

    private static FoodItem findMenuItemByName(String name) {
        for (FoodItem item : menu.getItems()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static FoodItem findMenuItemByName(Menu menu, String name) {
        for (FoodItem item : menu.getItems()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    private static void trackOrder(Customer customer) {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        Order order = admin.getOrderQueue().getOrderByID(orderId);
        if (order != null && order.getCustomer().equals(customer)) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Items:");
            for (FoodItem item : order.getItems().keySet()) {
                System.out.println(item.getName() + " x" + order.getItems().get(item) + " - $" + item.getPrice()*order.getItems().get(item));
            }
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total: $" + order.getTotal());
        } else {
            System.out.println("Order not found.");
        }
    }
    public static Admin getAdmin() {
        return admin;
    }
    private static void manageMenu() {
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. Update Menu Item");
        System.out.println("4. View Menu");
        System.out.println("5. Restock Menu Item");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        switch (choice) {
            case 1:
                System.out.print("Enter item name: ");
                String name = scanner.nextLine();
                System.out.print("Enter item price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter item category: ");
                String category = scanner.nextLine();
                menu.addItem(new FoodItem(name, price, category, true));
                System.out.println("Item added successfully.");
                break;
            case 2:
                System.out.print("Enter item name to remove: ");
                String itemName = scanner.nextLine();
                FoodItem item = findMenuItemByName(itemName);
                if (item != null) {
                    menu.removeItem(item);
                    System.out.println("Item removed successfully.");
                } else {
                    System.out.println("Item not found.");
                }
                break;
            case 3:
                System.out.print("Enter item name to update: ");
                String itemNameToUpdate = scanner.nextLine();
                FoodItem itemToUpdate = findMenuItemByName(itemNameToUpdate);
                if (itemToUpdate != null) {
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();
                    itemToUpdate.setPrice(newPrice);
                    System.out.println("Item updated successfully.");
                } else {
                    System.out.println("Item not found.");
                }
                break;
            case 4:
                menu.displayMenu();
                break;
            case 5:
                System.out.print("Enter item name to restock: ");
                String itemNameToRestock = scanner.nextLine();
                FoodItem itemToRestock = findMenuItemByName(itemNameToRestock);
                if (itemToRestock != null) {
                    System.out.print("Enter quantity to add: ");
                    int quantityToAdd = scanner.nextInt();
                    scanner.nextLine();
                    itemToRestock.increaseQuantity(quantityToAdd);
                    System.out.println("Item restocked successfully.");
                } else {
                    System.out.println("Item not found.");
                }
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.println();
    }
}