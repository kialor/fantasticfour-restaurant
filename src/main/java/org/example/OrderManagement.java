package org.example;

import java.util.*;

public class OrderManagement {
    private List<Order> orders;
    private InventoryManagementSystem inventorySystem;

    public OrderManagement() {
        this.orders = new ArrayList<>();
        this.inventorySystem = new InventoryManagementSystem();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void updateOrderStatus(int orderId, Status newStatus) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(newStatus);
                break;
            }
        }
        System.out.println("Order status updated successfully.");
        System.out.println();
    }

    public boolean doesOrderExist(int orderId) {

        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return true; // Order exists
            }
        }

        return false; // Order does not exist
    }

    public List<Order> getOrders () {
        return orders;
    }

    public void createOrder(Menu menu) {
        // Generate a random order ID
        int orderId = generateRandomOrderId();

        List<MenuItem> menuItems = menu.getMenuItems();
        List<MenuItem> orderItems = new ArrayList<>();

        // Display the menu items and prompt the user to select items and quantities
        MenuView menuView = new MenuView();
        menuView.displayMenu(menuItems);

        // Get user input
        Scanner scanner = new Scanner(System.in);
        String itemName;
        int quantity;

        while (true) {
            System.out.print("Enter the name of the item to add (or 'done' to finish): ");
            itemName = scanner.nextLine();

            if (itemName.equalsIgnoreCase("done")) {
                break;
            }

            MenuItem item = menu.getMenuItemByName(itemName);

            if (item == null) {
                System.out.println("Invalid item name. Please try again.");
                continue;
            }

            System.out.print("Enter the quantity: ");
            quantity = scanner.nextInt();
            scanner.nextLine();

            orderItems.add(new MenuItem(item, quantity));

            // Update the ingredient usage in the inventory management system
            Map<String, Integer> ingredientsUsed = new HashMap<>();
            ingredientsUsed.put(itemName, quantity);
            inventorySystem.processOrder(ingredientsUsed);
        }


        Order order = new Order(orderId, orderItems);
        addOrder(order);

        System.out.println("Order created successfully.");
        System.out.println();
    }

    private int generateRandomOrderId() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("--Orders--\n");
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Items Ordered:");
                for (MenuItem item : order.getItems()) {
                    System.out.println("- Item: " + item.getItemName());
                    System.out.println("  Quantity: " + item.getItemQuantity());
                }
                System.out.println("Total Price: $" + order.getTotalPrice());
                System.out.println("Status: " + order.getStatus());
                System.out.println();
            }
        }
    }


}