package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {
    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
    }

    public void addItem(MenuItem item){
        menuItems.add(item);
    }

    public void removeItem(MenuItem item) {
        menuItems.remove(item);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public MenuItem getMenuItemByName(String itemName) {
        for (MenuItem item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }


    //reads each line from file until no more lines
    //splits line of text where hyphen is located into an array of strings
    public void loadMenuFromFile(String relativePath) {
        String basePath = System.getProperty("user.dir");
        String absolutePath = new File(basePath, relativePath).getAbsolutePath();

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                String itemName = parts[0].trim();
                String itemDescription = parts[1].trim();
                int preparationTime = Integer.parseInt(parts[2].trim());
                double itemPrice = Double.parseDouble(parts[3].trim());
                //split string into substring when ";" is found
                //array of stringed ingredients converted into List of strings
                List<String> ingredients = Arrays.asList(parts[4].trim().split(";"));

                MenuItem item = new MenuItem(itemName, itemDescription, preparationTime, itemPrice, ingredients);
                menuItems.add(item);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while loading the menu from the file: " + e.getMessage());
        }
    }


    //concatenate item info, separated by "-" and join ingredients with ";" in txt file.
    public void saveMenuToFile(String relativePath) {
        String basePath = System.getProperty("user.dir");
        String absolutePath = new File(basePath, relativePath).getAbsolutePath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath))) {
            for (MenuItem item : menuItems) {
                String line = item.getItemName()+ "-" +
                        item.getItemDescription() + "-" +
                        item.getPrepTime() + "-" +
                        item.getItemPrice() + "-" +
                        String.join(";", item.getItemIngredients());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving the menu to the file: " + e.getMessage());
        }
    }
}
