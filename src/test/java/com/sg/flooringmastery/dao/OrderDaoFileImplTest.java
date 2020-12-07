package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoFileImplTest {
    private OrderDao testDao;

    @BeforeEach
    void setUp() throws Exception {
        String testFile = "FileData/TestFiles/TestOrders/";
        File dataFolder = new File(testFile);
        File[] fileList = dataFolder.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {
                file.delete();
            }
        }

        try (PrintWriter out1 = new PrintWriter(new FileWriter(testFile + "Orders_06012013.txt"));
             PrintWriter out2 = new PrintWriter(new FileWriter(testFile + "Orders_06022013.txt"))) {
            out1.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n" +
                    "1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06");
            out1.flush();
            out2.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n" +
                    "2,Doctor Who,WA,9.25,Wood,243.00,5.15,4.75,1251.45,1154.25,216.51,2622.21\n" +
                    "3,Albert Einstein,KY,6.00,Carpet,217.00,2.25,2.10,488.25,455.70,56.64,1000.59");
            out2.flush();

        } catch (IOException e) {
            throw new PersistenceException(
                    "Could not save Order test data into files", e);
        }
        testDao = new OrderDaoFileImpl(testFile);
    }

    @Test
    public void testAddOrder() throws Exception {
        //New date
        Order order1 = new Order(LocalDate.parse("2018-08-06"), "Adrianna", "CA", "Tile", new BigDecimal("209.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(4);

        Order retrievedOrder = testDao.addOrder(order1);
        assertEquals(retrievedOrder, order1);

        retrievedOrder = testDao.getOrder(order1.getOrderDate(), order1.getOrderNumber());
        assertEquals(retrievedOrder, order1);


        //Existing date
        Order order2 = new Order(LocalDate.parse("2013-01-06"), "MyCompany", "CA", "Tile", new BigDecimal("209.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order2.setOrderNumber(5);

        retrievedOrder = testDao.addOrder(order2);
        assertEquals(retrievedOrder, order2);

        retrievedOrder = testDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());
        assertEquals(retrievedOrder, order2);

    }

    @Test
    public void testGetOrder() {
        //Order in the file
        Order order1 = new Order(LocalDate.parse("2013-01-06"), "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(1);

        Order retrievedOrder = testDao.getOrder(order1.getOrderDate(), order1.getOrderNumber());
        assertNotNull(retrievedOrder);
        assertEquals(order1, retrievedOrder);

        //Order not in the file
        Order order2 = new Order(LocalDate.parse("2013-01-06"), "Adrianna", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order2.setOrderNumber(2);

        retrievedOrder = testDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());
        assertNull(retrievedOrder);

    }

    @Test
    public void testEditOrder() throws Exception {
        Order order2 = new Order(LocalDate.parse("2013-02-06"), "Doctor Me", "WA", "Wood", new BigDecimal("120.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);
        Order originalOrder = testDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());

        testDao.editOrder(order2);
        Order retrievedOrder = testDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());

        assertEquals(order2, retrievedOrder);
        assertNotEquals(order2, originalOrder);

    }

    @Test
    public void testGetOrdersForDate() {
        //Existing orders
        LocalDate date1 = LocalDate.parse("2013-01-06");
        Order order1 = new Order(date1, "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(1);
        LocalDate date2 = LocalDate.parse("2013-02-06");
        Order order2 = new Order(date2, "Doctor Who", "WA", "Wood", new BigDecimal("243.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);
        Order order3 = new Order(date2, "Albert Einstein", "KY", "Carpet", new BigDecimal("217.00"), new BigDecimal("6.00"), new BigDecimal("2.25"), new BigDecimal("2.10"));
        order3.setOrderNumber(3);

        //Test for date1
        List<Order> retrievedOrders = testDao.getOrdersForDate(date1);
        assertEquals(1, retrievedOrders.size());
        assertTrue(retrievedOrders.contains(order1));
        assertFalse(retrievedOrders.contains(order2));
        assertFalse(retrievedOrders.contains(order3));

        //Test for date2
        retrievedOrders = testDao.getOrdersForDate(date2);
        assertEquals(2, retrievedOrders.size());
        assertFalse(retrievedOrders.contains(order1));
        assertTrue(retrievedOrders.contains(order2));
        assertTrue(retrievedOrders.contains(order3));

        //No orders for the date
        LocalDate date3 = LocalDate.parse("2014-12-10");
        retrievedOrders = testDao.getOrdersForDate(date3);
        assertNull(retrievedOrders);

    }

    @Test
    public void testGetAllOrders() {
        LocalDate date1 = LocalDate.parse("2013-01-06");
        Order order1 = new Order(date1, "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(1);
        LocalDate date2 = LocalDate.parse("2013-02-06");
        Order order2 = new Order(date2, "Doctor Who", "WA", "Wood", new BigDecimal("243.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);
        Order order3 = new Order(date2, "Albert Einstein", "KY", "Carpet", new BigDecimal("217.00"), new BigDecimal("6.00"), new BigDecimal("2.25"), new BigDecimal("2.10"));
        order3.setOrderNumber(3);

        Map<LocalDate, Map<Integer, Order>> allOrders = testDao.getAllOrders();
        assertTrue(allOrders.containsKey(date1));
        assertTrue(allOrders.get(date1).containsKey(order1.getOrderNumber()));
        assertTrue(allOrders.get(date1).containsValue(order1));
        assertTrue(allOrders.containsKey(date2));
        assertTrue(allOrders.get(date2).containsKey(order2.getOrderNumber()));
        assertTrue(allOrders.get(date2).containsValue(order2));
        assertTrue(allOrders.get(date2).containsKey(order3.getOrderNumber()));
        assertTrue(allOrders.get(date2).containsValue(order3));
    }

    @Test
    public void testRemoveOrder() throws Exception {
        Order order2 = new Order(LocalDate.parse("2013-02-06"), "Doctor Who", "WA", "Wood", new BigDecimal("243.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);
        Order retrievedOrder = testDao.removeOrder(order2.getOrderDate(), order2.getOrderNumber());
        assertEquals(order2, retrievedOrder);
        retrievedOrder = testDao.getOrder(order2.getOrderDate(), order2.getOrderNumber());
        assertNull(retrievedOrder);

    }
}