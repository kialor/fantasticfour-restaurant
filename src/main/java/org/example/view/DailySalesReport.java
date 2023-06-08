package org.example.view;

import org.example.controllers.OrderManagement;
import org.example.models.MenuItem;
import org.example.models.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class DailySalesReport {
    private LocalDate date;
    private double totalRevenue;
    private Map<String, Integer> popularItems;
    private Map<String, Double> tableSales;
    private List<Order> orders;

    public DailySalesReport(LocalDate date) {
        this.date = date;
        this.totalRevenue = 0;
        this.popularItems = new HashMap<>();
        this.tableSales = new LinkedHashMap<>();
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
        updateTotalRevenue(order.getTotalPrice());
        updatePopularItems(order.getItems());
        updateTableSales(order.getTableID(), order.getTotalPrice());
    }

    private void updateTotalRevenue(double amount) {
        totalRevenue += amount;
    }

    private void updatePopularItems(List<MenuItem> items) {
        for (MenuItem item : items) {
            String itemName = item.getItemName();
            popularItems.put(itemName, popularItems.getOrDefault(itemName, 0) + item.getItemQuantity());
        }
    }

    private void updateTableSales(String tableID, double amount) {
        tableSales.put(tableID, tableSales.getOrDefault(tableID, 0.0) + amount);
    }

    public void exportReport(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(getFormattedReport());
            System.out.println("Daily sales report exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting daily sales report: " + e.getMessage());
        }
    }

    private String getFormattedReport() {
        StringBuilder report = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        report.append("-------------------------------\n");
        report.append("Daily Sales Report\n");
        report.append("Date: ").append(date.toString()).append("\n");
        report.append("-------------------------------\n");
        report.append("Total Revenue: $").append(decimalFormat.format(totalRevenue)).append("\n\n");

        report.append("Most Popular Items:\n");
        int itemCount = 1;
        for (Map.Entry<String, Integer> entry : getSortedItemsByPopularity()) {
            report.append(itemCount++).append(". ").append(entry.getKey()).append(": ").append(entry.getValue())
                    .append(" orders\n");
        }
        report.append("\n");

        report.append("Table Sales:\n");
        int tableCount = 1;
        for (Map.Entry<String, Double> entry : getSortedTableSales()) {
            report.append(tableCount++).append(". ").append("Table ").append(entry.getKey()).append(": $")
                    .append(decimalFormat.format(entry.getValue())).append("\n");
        }
        report.append("\n");

        report.append("Detailed Orders:\n");
        for (Order order : orders) {
            report.append("Order ID: #").append(order.getOrderId()).append("\n");
            report.append("Table ID: ").append(order.getTableID()).append("\n");
            report.append("Items:\n");
            for (MenuItem item : order.getItems()) {
                report.append("  - ").append(item.getItemName()).append(": ").append(item.getItemQuantity())
                        .append(" ($").append(decimalFormat.format(item.getItemPrice())).append(")\n");
            }
            report.append("Total: $").append(decimalFormat.format(order.getTotalPrice())).append("\n\n");
        }

        return report.toString();
    }

    private List<Map.Entry<String, Integer>> getSortedItemsByPopularity() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(popularItems.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return entries;
    }

    private List<Map.Entry<String, Double>> getSortedTableSales() {
        List<Map.Entry<String, Double>> entries = new ArrayList<>(tableSales.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return entries;
    }
}

