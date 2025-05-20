package com.example.assignment4;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class FoodItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double price;
    private String category;
    private boolean available;
    private List<String> reviews;
    private int quantity;
    private double rating;
    private int ratingCount;

    public FoodItem(String name, double price, String category, boolean available, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.reviews = new ArrayList<>();
        this.quantity = quantity;
        this.rating = 0;
        this.ratingCount = 0;
    }


    public FoodItem(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.reviews = new ArrayList<>();
        this.quantity = 10;
    }


    public void addReview(String review, Customer customer) {
        reviews.add(review + " - " + customer.getName());
    }
    public void addRating(int rating) {
        this.rating = (this.rating * ratingCount + rating) / (ratingCount + 1);
        ratingCount++;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }
    public boolean isAvailable() {
        return available;
    }
    public List<String> getReviews() {
        return reviews;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void decreaseQuantity() {
        quantity--;
        if (quantity < 0) {
            quantity = -1;
            available = false;
        }
    }
    public void decreaseQuantity(int n) {
        quantity -= n;
        if (quantity < 0) {
            quantity = -1;
            available = false;
        }
    }
    public void increaseQuantity() {
        if (quantity < 0) {
            quantity = 1;
            available = true;
        } else {
            quantity++;
            available = true;
        }
    }
    public void increaseQuantity(int n) {
        if (quantity < 0) {
            quantity = n;
            available = true;
        } else {
            quantity += n;
            available = true;
        }
    }

}
