package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String itemName;
    private String itemDescription;
    private int prepTime;
    private double itemPrice;
    private List<String> itemIngredients;
    private int itemQuantity;


    //Constructor
    public MenuItem() {
    }
    public MenuItem(String itemName, String itemDescription, int prepTime, double itemPrice, List<String> itemIngredients) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.prepTime = prepTime;
        this.itemPrice = itemPrice;
        this.itemIngredients = itemIngredients;
    }



    // Second constructor

    public MenuItem(String itemName, double itemPrice, int itemQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemDescription = "";
        this.prepTime = 0;
        this.itemIngredients = new ArrayList<>();
    }

    public MenuItem(MenuItem item, int quantity) {
        this.itemName = item.itemName;
        this.itemDescription = item.itemDescription;
        this.prepTime = item.prepTime;
        this.itemPrice = item.itemPrice;
        this.itemIngredients = item.itemIngredients;
        this.itemQuantity = quantity;
    }


    //Getters


    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public List<String> getItemIngredients() {
        return itemIngredients;
    }
    public int getItemQuantity() {
        return itemQuantity;
    }

    //Setters

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemIngredients(List<String> itemIngredients) {
        this.itemIngredients = itemIngredients;
    }

    public void setItemQuantity(int quantity) {
        this.itemQuantity = quantity;
    }
}
