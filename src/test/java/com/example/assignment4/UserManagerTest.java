package com.example.assignment4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
        userManager.checkAndSaveUser("Alice", "123");
        userManager.checkAndSaveUser("Bob", "456");
    }

    @Test
    public void testValidLogin() {
        boolean result = userManager.authenticateUser("Alice", "123");
        assertTrue(result, "Expected Alice to be authenticated");
    }

    @Test
    public void testInvalidUsername() {
        // Invalid username
        boolean result = userManager.authenticateUser("Charlie", "123");
        assertFalse(result, "Expected Charlie to not be authenticated");
    }

    @Test
    public void testInvalidLogin() {
        // Invalid password
        boolean result = userManager.authenticateUser("Alice", "456");
        assertFalse(result, "Expected Alice to not be authenticated");
    }

    @Test
    public void testUserExists() {
        boolean result = userManager.userExists("Alice");
        assertTrue(result, "Expected Alice to exist");
    }

    @Test
    public void testUserDoesNotExist() {
        boolean result = userManager.userExists("Charlie");
        assertFalse(result, "Expected Charlie to not exist");
    }
}