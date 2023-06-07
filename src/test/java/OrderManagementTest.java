import org.example.controllers.OrderManagement;
import org.example.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderManagementTest {
    private OrderManagement orderManagement;

    @BeforeEach
    public void setUp() {
        orderManagement = new OrderManagement();
    }

    @Test
    public void testAddOrder() {
        // Create a sample order
        Order order = new Order(33);

        // Add the order to the order management
        orderManagement.addOrder(order);

        // Verify that the order is added correctly
        assertEquals(1, orderManagement.getOrders().size());
        assertEquals(order, orderManagement.getOrders().get(0));
    }
}
