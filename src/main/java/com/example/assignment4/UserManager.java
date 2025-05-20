package com.example.assignment4;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USERS_FILE = "users.txt";
    private final Map<String, String> users = new HashMap<>(); // Store username-password pairs

    public UserManager() {
        loadUsers();
    }

    // Check if a user exists
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    // Authenticate existing user
    public boolean authenticateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    // Register a new user
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, password);
        saveUserToFile(username, password);
        return true;
    }

    // Load all users from the file
    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Users file not found. Starting fresh.");
        }
    }

    // Save a new user to the file
    private void saveUserToFile(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
    // Check if a user exists, if not save the user using given info
    public boolean checkAndSaveUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, password);
        saveUserToFile(username, password);
        return true;
    }

}
