package org.example;


import org.example.controllers.InventoryManagementSystem;
import org.example.controllers.MenuController;
import org.example.controllers.OrderManagement;
import org.example.models.MenuItem;
import org.example.models.Status;
import org.example.models.User;
import org.example.utils.Menu;
import org.example.view.MenuView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ArrayList<User> allUsers = new ArrayList<>();
    private static final InventoryManagementSystem inventorySystem = new InventoryManagementSystem(); // Instantiate here

    public static void main(String[] args) {

        User manager = new User("Manager", "12345", "Manager");
        String hashedPassword = hashPassword(manager.number);
        allUsers.add(manager);
        User staff = new User("Staff", "678910", "Staff");
        String hashedPassword1 = hashPassword(staff.number);
        allUsers.add(staff);

        //MENU MANAGEMENT
        //create instance of Menu class -> creates empty menu object
        //load txt file
        //create instance of MenuController class, pass menu as parameter
        Menu menu = new Menu();
        menu.loadMenuFromFile("src\\main\\java\\org\\example\\menu.txt");
        MenuController menuController = new MenuController(menu);
        Scanner scanner = new Scanner(System.in);

        OrderManagement orderManagement = new OrderManagement();

        inventorySystem.addIngredient("Potato", 7);
        inventorySystem.addIngredient("beef", 8);
        inventorySystem.addIngredient("shrimp", 8);
        inventorySystem.addIngredient("bell pepper", 6);

        boolean start = true;

        while (start) {
            System.out.println("Hello, Welcome to the Restaurant Management System");
            System.out.println("Please enter your username: ");
            String usernameinput = scanner.nextLine();
            System.out.println("Please enter your password: ");
            String passwordinput = scanner.nextLine();
            boolean passwordMatches = verifyPassword(passwordinput, hashedPassword);
                while ((usernameinput.equals("Manager") && passwordMatches)) {
                    System.out.println("Welcome Manager!");
                    System.out.println("Restaurant Management System\n");
                    System.out.println("Menu Options");
                    System.out.println("1. View Menu");
                    System.out.println("2. Add Item");
                    System.out.println("3. Remove Item");
                    System.out.println("4. Edit Item\n");
                    System.out.println("Order Options");
                    System.out.println("5. Create Order");
                    System.out.println("6. Update Order Status");
                    System.out.println("7. Display Orders\n");
                    System.out.println("8. Exit\n");
                    System.out.print("Enter your choice: ");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            List<MenuItem> menuItems = menu.getMenuItems();
                            MenuView menuView = new MenuView();
                            menuView.displayMenu(menuItems);
                            break;
                        case 2:
                            menuController.addMenuItem();
                            break;
                        case 3:
                            menuController.removeMenuItem();
                            break;
                        case 4:
                            menuController.editMenuItem();
                            break;
                        case 5:
                            orderManagement.createOrder(menu);
                            break;
                        case 6:
                            System.out.print("Enter the order ID: ");
                            int orderId = scanner.nextInt();
                            scanner.nextLine();

                            // Check if the order ID exists
                            if (orderManagement.doesOrderExist(orderId)) {
                                System.out.println("Select the new status:");
                                for (Status status : Status.values()) {
                                    System.out.println(status.ordinal() + ". " + status);
                                }

                                System.out.print("Enter the new status: ");
                                String statusInput = scanner.nextLine();

                                Status newStatus = Status.valueOf(statusInput.toUpperCase());
                                if (newStatus != null) {
                                    orderManagement.updateOrderStatus(orderId, newStatus);
                                } else {
                                    System.out.println("Invalid status. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid order number.");
                            }
                            break;
                        case 7:
                            orderManagement.displayOrders();
                            break;
                        case 8:
                            menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again!");
                            break;
                    }
                    if (choice == 8) {
                        break;
                    }
                }

                boolean passwordMatches2 = verifyPassword(passwordinput, hashedPassword1);
                while (usernameinput.equals("Staff") && passwordMatches2) {
                    System.out.println("Welcome Staff!");
                    System.out.println("Restaurant Management System\n");
                    System.out.println("Menu Options");
                    System.out.println("1. View Menu");
                    System.out.println("2. Add Item");
                    System.out.println("3. Remove Item");
                    System.out.println("4. Edit Item\n");
                    System.out.println("Order Options");
                    System.out.println("5. Create Order");
                    System.out.println("6. Update Order Status");
                    System.out.println("7. Display Orders\n");
                    System.out.println("8. Exit\n");
                    System.out.print("Enter your choice: ");
                    int choice2 = Integer.parseInt(scanner.nextLine());


                    switch (choice2) {
                        case 1:
                            List<MenuItem> menuItems = menu.getMenuItems();
                            MenuView menuView = new MenuView();
                            menuView.displayMenu(menuItems);
                            break;
                        case 2:
                            menuController.addMenuItem();
                            break;
                        case 3:
                            menuController.removeMenuItem();
                            break;
                        case 4:
                            menuController.editMenuItem();
                            break;
                        case 5:
                            orderManagement.createOrder(menu);
                            break;
                        case 6:
                            System.out.print("Enter the order ID: ");
                            int orderId = scanner.nextInt();
                            scanner.nextLine();

                            // Check if the order ID exists
                            if (orderManagement.doesOrderExist(orderId)) {
                                System.out.println("Select the new status:");
                                for (Status status : Status.values()) {
                                    System.out.println(status.ordinal() + ". " + status);
                                }

                                System.out.print("Enter the new status: ");
                                String statusInput = scanner.nextLine();

                                Status newStatus = Status.valueOf(statusInput.toUpperCase());
                                if (newStatus != null) {
                                    orderManagement.updateOrderStatus(orderId, newStatus);
                                } else {
                                    System.out.println("Invalid status. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid order number.");
                            }
                            break;
                        case 7:
                            orderManagement.displayOrders();
                            break;
                        case 8:
                            menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    if (choice2 == 8) {
                        break;
                    }
                }
            }
        }


        public static String hashPassword (String password){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                return bytesToHex(hashBytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static String bytesToHex ( byte[] bytes){
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        }

        public static boolean verifyPassword (String password, String hashedPassword){
            String hashedInput = hashPassword(password);
            return hashedInput != null && hashedInput.equals(hashedPassword);
        }

    }
