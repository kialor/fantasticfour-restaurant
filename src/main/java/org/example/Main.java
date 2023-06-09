package org.example;


import org.example.controllers.InventoryManagementSystem;
import org.example.controllers.MenuController;
import org.example.controllers.OrderManagement;
import org.example.models.MenuItem;
import org.example.models.Status;
import org.example.models.Tables;
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
    public static final String RESET = "\033[0m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    private static final ArrayList<User> allUsers = new ArrayList<>();
    private static final InventoryManagementSystem inventorySystem = new InventoryManagementSystem(); // Instantiate here
    private static final ArrayList<Tables> allTables = new ArrayList<>();
    private static void exportDailySalesReport(MenuController menuController, int tableNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter filename to export the daily sales report: ");
        String filename = scanner.nextLine();
        menuController.getSalesReport().exportReport(filename);
    }
    public static void main(String[] args) {

        User manager = new User("Manager", "12345", "Manager");
        String hashedPassword = hashPassword(manager.number);
        allUsers.add(manager);
        User staff = new User("Staff", "678910", "Staff");
        String hashedPassword1 = hashPassword(staff.number);
        allUsers.add(staff);
        
        Tables tables = new Tables(1, 10, "Open", null);
        Tables tables1 = new Tables(2, 20, "Open", null);
        Tables tables2 = new Tables(3, 30, "Open", null);
        
        allTables.add(tables);
        allTables.add(tables1);
        allTables.add(tables2);

        int tableNumber = 0;

        //MENU MANAGEMENT
        //create instance of Menu class -> creates empty menu object
        //load txt file
        //create instance of MenuController class, pass menu as parameter
        Menu menu = new Menu();
        menu.loadMenuFromFile("src\\main\\java\\org\\example\\menu.txt");
        MenuController menuController = new MenuController(menu);
        Scanner scanner = new Scanner(System.in);

        OrderManagement orderManagement = new OrderManagement(menuController);

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
            if (!passwordMatches) {
                System.out.println("Sorry that was an incorrect username/password combination");
            } else {
                while ((usernameinput.equals("Manager") && passwordMatches)) {
                    System.out.println("Welcome Manager!");
                    System.out.println("Restaurant Management System\n");
                    System.out.println(PURPLE_BOLD+"Menu Options"+RESET);
                    System.out.println("1. View Menu");
                    System.out.println("2. Add Item");
                    System.out.println("3. Remove Item");
                    System.out.println("4. Edit Item\n");
                    System.out.println(CYAN_BOLD+"Order Options"+RESET);
                    System.out.println("5. Create Order");
                    System.out.println("6. Update Order Status");
                    System.out.println("7. Display Orders\n");
                    System.out.println("8. View Sales Report\n");
                    System.out.println(GREEN_BOLD+"Table Options"+RESET);
                    System.out.println("9. All Table's Status");
                    System.out.println("10. Assign Table To Customer");
                    System.out.println("11. Clear Table\n");
                    System.out.println("12. Exit\n");
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
                            exportDailySalesReport(menuController, tableNumber);
                            break;
                        case 9:
                            allTables(allTables);
                            break;
                        case 10:
                            System.out.println("Enter the customer's name: ");
                            String customer = scanner.nextLine();
                            System.out.println("Enter your party size: ");
                            int response = Integer.parseInt(scanner.nextLine());
                            allTables(allTables);
                            System.out.println("Which table ID would you like to assign " + customer + " to: ");
                            int table = Integer.parseInt(scanner.nextLine());
                            for (Tables item : allTables) {
                                if (item.getTableID() == table) {
                                    if (item.getTableSize() >= response) {
                                        if (item.getStatus().equals("Open")) {
                                            item.addCustomer(customer, table);
                                        } else {
                                            System.out.println("This table is currently occupied");
                                        }
                                    } else {
                                        System.out.println("Your party is too large for this table");
                                    }
                                }
                            }
                            break;
                        case 11: 
                            System.out.println("Which table would you like to clear? ");
                            int cleartable = Integer.parseInt(scanner.nextLine());
                            for (Tables item : allTables) {
                                if (item.getTableID() == cleartable) {
                                    item.clearTable();
                                }
                            }
                            break;
                        case 12:
                            menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again!");
                            break;
                    }
                    if (choice == 12) {
                        break;
                    }
                }
            }

                boolean passwordMatches2 = verifyPassword(passwordinput, hashedPassword1);
            if (!passwordMatches2) {
                System.out.println("Sorry that was an incorrect username/password combination");
            } else {
                while (usernameinput.equals("Staff") && passwordMatches2) {
                    System.out.println("Welcome Staff!");
                    System.out.println("Restaurant Management System\n");
                    System.out.println(PURPLE_BOLD+"Menu Options"+RESET);
                    System.out.println("1. View Menu");
                    System.out.println("2. Add Item");
                    System.out.println("3. Remove Item");
                    System.out.println("4. Edit Item\n");
                    System.out.println(CYAN_BOLD+"Order Options"+RESET);
                    System.out.println("5. Create Order");
                    System.out.println("6. Update Order Status");
                    System.out.println("7. Display Orders\n");
                    System.out.println(GREEN_BOLD+"Table Options"+RESET);
                    System.out.println("8. All Table's Status");
                    System.out.println("9. Assign Table To Customer");
                    System.out.println("10. Clear Table\n");
                    System.out.println("11. Exit\n");
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
                            allTables(allTables);
                            break;
                        case 9:  
                            System.out.println("Enter the customer's name: ");
                            String customer = scanner.nextLine();
                            System.out.println("Enter your party size: ");
                            int response = Integer.parseInt(scanner.nextLine());
                            allTables(allTables);
                            System.out.println("Which table ID would you like to assign " + customer + " to: ");
                            int table = Integer.parseInt(scanner.nextLine());
                            for (Tables item : allTables) {
                                if (item.getTableID() == table) {
                                    if (item.getTableSize() >= response) {
                                        if (item.getStatus().equals("Open")) {
                                            item.addCustomer(customer, table);
                                        } else {
                                            System.out.println("This table is currently occupied");
                                        }
                                    } else {
                                        System.out.println("Your party is too large for this table");
                                    }
                                }
                            }
                            break;
                        case 10:
                            System.out.println("Which table would you like to clear? ");
                            int cleartable = Integer.parseInt(scanner.nextLine());
                            for (Tables item : allTables) {
                                if (item.getTableID() == cleartable) {
                                    item.clearTable();
                                }
                            }
                            break;
                        case 11: 
                            menu.saveMenuToFile("src\\main\\java\\org\\example\\menu.txt");
                            System.out.println("Exiting.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    if (choice2 == 11) {
                        break;
                    }
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
   
        public static void allTables(List<Tables> allTables) {
        System.out.println("--All Tables--");
        System.out.println();
        for (Tables item : allTables) {
            System.out.println("Table ID: " + item.getTableID());
            System.out.println("Table Max Party Size: " + item.getTableSize());
            System.out.println("Table Availability: " + item.getStatus());
            if (item.getStatus() != "Open") {
                System.out.println("Table Occupant: " + item.getCustomerAssigned());
            }
            System.out.println();
        }
        }
    }
