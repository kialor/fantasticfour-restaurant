package org.example.controllers;

import org.example.utils.Menu;
import org.example.models.MenuItem;
import org.example.view.DailySalesReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private Menu menu;
    private Scanner scanner;
    private OrderManagement orderManagement;
    private DailySalesReport salesReport;

    public MenuController(Menu menu) {
        this.menu = menu;
        scanner = new Scanner(System.in);
        orderManagement = new OrderManagement(null);
        salesReport = new DailySalesReport(LocalDate.now());
    }

    //Adding menu item

    public void addMenuItem() {
        System.out.println("Enter the details of the new menu item");

        System.out.print("Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Item Description: ");
        String itemDescription = scanner.nextLine();

        System.out.print("Preparation Time: ");
        int preparationTime = scanner.nextInt();

        System.out.print("Item Price: ");
        double itemPrice = scanner.nextDouble();
        scanner.nextLine();

        //Splits ingredientsString into an array of String elements
        //"," used as the delimiter and stores the substrings in array
        //converts ingredientsArray into List<String>

        System.out.print("Ingredients: ");
        String ingredientsString = scanner.nextLine();
        String[] ingredientsArray = ingredientsString.split(",");
        List<String> ingredients = Arrays.asList(ingredientsArray);

       //create new MenuItem object, add, and save to txt file
        MenuItem newItem = new MenuItem(itemName, itemDescription, preparationTime, itemPrice, ingredients);
        menu.addItem(newItem);
        menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");

        System.out.println("New menu item added successfully.\n");
    }
    public DailySalesReport getSalesReport() {
        return salesReport;
    }
    public void removeMenuItem() {
        System.out.print("Enter the name of the menu item to remove: ");
        String itemName = scanner.nextLine();

        //retrieve current List of MenuItem objects
        List<MenuItem> menuItems = menu.getMenuItems();
        MenuItem itemToRemove = null;


        //iterate ove each MenuItem object in menuItems list
        //if MenuItem object in list matches itemName, reassigns to itemToRemove
        for (MenuItem item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                itemToRemove = item;
                break;
            }
        }

        //if matching menu item is found, remove item and save txt file
        //if loop completes without a match -> no found
        if (itemToRemove != null) {
            menu.removeItem(itemToRemove);
            menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");
            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }

    //Edit menu item
    public void editMenuItem() {
        System.out.print("Enter the name of the menu item to edit: ");
        String itemName = scanner.nextLine();

        List<MenuItem> menuItems = menu.getMenuItems();
        MenuItem itemToEdit = null;

        for (MenuItem item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                itemToEdit = item;
                break;
            }
        }

        if (itemToEdit != null) {
            System.out.println("Enter the new details for the menu item");

            System.out.print("New Item Name: ");
            String newitemName = scanner.nextLine();
            itemToEdit.setItemName(newitemName);

            System.out.print("New Item Description: ");
            String newitemDescription = scanner.nextLine();
            itemToEdit.setItemDescription(newitemDescription);

            System.out.print("New Preparation Time: ");
            int newpreparationTime = scanner.nextInt();
            itemToEdit.setPrepTime(newpreparationTime);

            System.out.print("New Item Price: ");
            double newitemPrice = scanner.nextDouble();
            itemToEdit.setItemPrice(newitemPrice);
            scanner.nextLine();

            System.out.print("New Ingredients: ");
            String newingredientsString = scanner.nextLine();
            String[] newingredientsArray = newingredientsString.split(",");
            List<String> newingredients = Arrays.asList(newingredientsArray);
            itemToEdit.setItemIngredients(newingredients);
            menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");

            System.out.println("Menu item edited successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }
}

