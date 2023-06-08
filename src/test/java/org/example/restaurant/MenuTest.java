package org.example.restaurant;
import org.example.models.MenuItem;
import org.example.utils.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    private Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new Menu();
    }

    @Test
    public void testAddItem() {

        String itemName = "Burger";
        double itemPrice = 10.99;
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);

        menu.addItem(menuItem);
        List<MenuItem> menuItems = menu.getMenuItems();

        assertEquals(1, menuItems.size());
        assertTrue(menuItems.contains(menuItem));
    }

    @Test
    public void testRemoveItem() {

        String itemName = "Burger";
        double itemPrice = 10.99;
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(itemName);
        menuItem.setItemPrice(itemPrice);

        menu.addItem(menuItem);
        menu.removeItem(menuItem);

        List<MenuItem> menuItems = menu.getMenuItems();

        assertEquals(0, menuItems.size());
        assertFalse(menuItems.contains(menuItem));
    }

    @Test
    public void testGetMenuItems() {

        List<MenuItem> menuItems = menu.getMenuItems();
        assertNotNull(menuItems);
        assertEquals(0, menuItems.size());
    }

}
