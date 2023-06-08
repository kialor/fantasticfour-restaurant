package org.example.restaurant;

import org.example.controllers.MenuController;
import org.example.controllers.OrderManagement;
import org.example.models.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;


public class OrderManagementTest {
    private OrderManagement orderManagement;
    private Order existingOrder;
    private Order nonExistingOrder;

    @BeforeEach
    public void setUp() {
        MenuController menuController = new MenuController(null); // Create a mock MenuController
        orderManagement = new OrderManagement(menuController);
        existingOrder = new Order(1, new ArrayList<>());
        nonExistingOrder = new Order(2, new ArrayList<>());
        orderManagement.addOrder(existingOrder);
    }

    @Test
    public void testDoesOrderExist() {
        // Test for an existing order
        boolean existingOrderExists = orderManagement.doesOrderExist(existingOrder.getOrderId());
        Assertions.assertTrue(existingOrderExists);

        // Test for a non-existing order
        boolean nonExistingOrderExists = orderManagement.doesOrderExist(nonExistingOrder.getOrderId());
        Assertions.assertFalse(nonExistingOrderExists);
    }
}