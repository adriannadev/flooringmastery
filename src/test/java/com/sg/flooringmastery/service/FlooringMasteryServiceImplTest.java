package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryServiceImplTest {

    private FlooringMasteryService service;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringmastery.service");
        appContext.refresh();
        service = appContext.getBean("flooringMasteryServiceImpl", FlooringMasteryServiceImpl.class);
    }

    @Test
    public void testAddOrderNewDate() throws Exception {
        Order order1 = new Order(LocalDate.parse("2020-11-06"), "Adrianna", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));

        Order addedOrder = service.addOrder(order1);
        Order retrievedOrder = service.getOrder(order1.getOrderDate(), order1.getOrderNumber());
        assertEquals(retrievedOrder, addedOrder);

    }

    @Test
    public void testAddOrderExistingDate() throws Exception {
        Order order1 = new Order(LocalDate.parse("2013-01-06"), "Adrianna", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));

        Order addedOrder = service.addOrder(order1);
        Order retrievedOrder = service.getOrder(order1.getOrderDate(), order1.getOrderNumber());
        assertEquals(retrievedOrder, addedOrder);

    }

    @Test
    public void testGetOrder() throws Exception {
        Order order2 = new Order(LocalDate.parse("2013-02-06"), "Doctor Who", "WA", "Wood", new BigDecimal("243.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);
        Order retrievedOrder = service.getOrder(LocalDate.parse("2013-02-06"), order2.getOrderNumber());
        assertEquals(retrievedOrder, order2);
    }

    @Test
    public void testGetOrderNoSuchOrderException() {
        Order order2 = new Order(LocalDate.parse("2013-02-06"), "Doctor Who", "WA", "Wood", new BigDecimal("243.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(10);

        assertThrows(NoSuchOrderException.class, () -> service.getOrder(LocalDate.parse("2013-02-06"), order2.getOrderNumber()));
    }

    @Test
    public void testEditOrder() throws Exception {
        Order order2 = new Order(LocalDate.parse("2013-02-06"), "Doctor Me", "WA", "Wood", new BigDecimal("200.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);

        service.editOrder(order2);
        Order retrievedOrder = service.getOrder(order2.getOrderDate(), order2.getOrderNumber());
        assertEquals(retrievedOrder, order2);

    }

    @Test
    public void testGetOrdersForDate() throws Exception {
        Order order1 = new Order(LocalDate.parse("2013-01-06"), "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(1);

        List<Order> retrievedOrders = service.getOrdersForDate(LocalDate.parse("2013-01-06"));
        assertEquals(1, retrievedOrders.size());
        assertTrue(retrievedOrders.contains(order1));
    }

    @Test
    public void testGetOrdersForDateNoSuchOrderException() {
        assertThrows(NoSuchOrderException.class, () -> service.getOrdersForDate(LocalDate.parse("2019-01-06")));
    }

    @Test
    public void testRemoveOrder() throws Exception {
        Order order1 = new Order(LocalDate.parse("2013-01-06"), "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(1);

        Order removedOrder = service.removeOrder(order1.getOrderDate(), order1.getOrderNumber());
        assertEquals(order1, removedOrder);

        assertThrows(NoSuchOrderException.class, () -> service.getOrder(order1.getOrderDate(), order1.getOrderNumber()));

    }

    @Test
    public void testGetAllTaxes() throws Exception {
        Tax tax1 = new Tax("TX", "Texas", new BigDecimal("4.45"));
        Tax tax2 = new Tax("WA", "Washington", new BigDecimal("9.25"));
        Tax tax3 = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        Tax tax4 = new Tax("CA", "California", new BigDecimal("25.00"));

        List<Tax> retrievedTaxes = service.getAllTaxes();

        assertEquals(4, retrievedTaxes.size());
        assertTrue(retrievedTaxes.contains(tax1));
        assertTrue(retrievedTaxes.contains(tax2));
        assertTrue(retrievedTaxes.contains(tax3));
        assertTrue(retrievedTaxes.contains(tax4));

        Tax tax5 = new Tax("NY", "New York", new BigDecimal("15.00"));

        assertFalse(retrievedTaxes.contains(tax5));


    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        Product product2 = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        Product product3 = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        Product product4 = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));

        List<Product> retrievedProducts = service.getAllProducts();
        assertEquals(4, retrievedProducts.size());
        assertTrue(retrievedProducts.contains(product1));
        assertTrue(retrievedProducts.contains(product2));
        assertTrue(retrievedProducts.contains(product3));
        assertTrue(retrievedProducts.contains(product4));

        Product product5 = new Product("Concrete", new BigDecimal("6.25"), new BigDecimal("8.75"));

        assertFalse(retrievedProducts.contains(product5));
    }
}