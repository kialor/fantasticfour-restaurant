package org.example.controllers;

import java.util.HashMap;
import java.util.Map;

public class InventoryManagementSystem {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private Map<String, Integer> ingredientStock;
    //This map will store the stock of ingredients, where the ingredient name will be the key
    // and the quantity will be the value
    public InventoryManagementSystem() {
        ingredientStock = new HashMap<>();
    }
    //This constructor initializes the ingredientStock map.

    public void addIngredient(String ingredientName, int initialStock) {
        ingredientStock.put(ingredientName, initialStock);
    }
    //method is used to add ingredients to the inventory

    public void processOrder(Map<String, Integer> ingredientsUsed)

    //This map represents the ingredients and their respective quantities used in an order.
    {
        for (Map.Entry<String, Integer> entry : ingredientsUsed.entrySet()) {
            String ingredientName = entry.getKey();
            int usedQuantity = entry.getValue();
            int currentStock = ingredientStock.getOrDefault(ingredientName, 15);
            int newStock = currentStock - usedQuantity;

            if (newStock < 5) { // Alert when the stock is running low (less than 5 units)
                System.out.println(ANSI_RED + "Alert: " + ingredientName + " is running low!" + ANSI_RESET);
            }

            ingredientStock.put(ingredientName, newStock);
        }
    }
}
