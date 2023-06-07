package org.example.models;

import java.text.DecimalFormat;
import java.util.List;

public class Order {
    private int orderId;
    private List<MenuItem> items;
    private double totalPrice;
    private Status status;

    public Order(int orderId, List<MenuItem> items) {
        this.orderId = orderId;
        this.items = items;
        calculateTotalPrice();
        this.status = Status.WAITING;
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (MenuItem item : items) {
            totalPrice += item.getItemPrice() * item.getItemQuantity();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        totalPrice = Double.parseDouble(decimalFormat.format(totalPrice));
    }

    public int getOrderId() {
        return orderId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
