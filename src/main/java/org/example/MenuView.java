package org.example;

import java.util.List;


public class MenuView {
    //Adding color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    //Displaying list named menuItems that contain objects of MenuItem class
    //Iterates over each MenuItem in menuItems list, assigns each element to item variable

    public void displayMenu(List<MenuItem> menuItems) {
        System.out.println("--Menu--");
        System.out.println();
        for (MenuItem item : menuItems) {
            System.out.println(ANSI_BLUE+item.getItemName()+ANSI_RESET +"   $"+ item.getItemPrice());
            System.out.println(item.getItemDescription());
            System.out.println("Preparation time: " +item.getPrepTime() +" minutes");
            System.out.println(item.getItemIngredients());
            System.out.println();
        }
    }
}
