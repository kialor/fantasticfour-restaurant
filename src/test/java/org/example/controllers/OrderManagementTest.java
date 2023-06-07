package org.example.controllers;

import org.example.controllers.MenuController;
import org.example.controllers.OrderManagement;
import org.example.models.Order;
import org.example.models.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementTest {
    private OrderManagement orderManagement;
    private Order existingOrder;
    private Order nonExistingOrder;

    @Before
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
        Assert.assertTrue(existingOrderExists);

        // Test for a non-existing order
        boolean nonExistingOrderExists = orderManagement.doesOrderExist(nonExistingOrder.getOrderId());
        Assert.assertFalse(nonExistingOrderExists);
    }
}
