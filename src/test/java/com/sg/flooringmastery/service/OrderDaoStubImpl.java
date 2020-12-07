package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoStubImpl implements OrderDao {

    private static int largestOrderNumber = 0;
    private final Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

    public OrderDaoStubImpl() {
        Map<Integer, Order> ordersForDate1 = new HashMap<>();
        Order order1 = new Order(LocalDate.parse("2013-01-06"), "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"), new BigDecimal("25.00"), new BigDecimal("3.50"), new BigDecimal("4.15"));
        order1.setOrderNumber(1);
        ordersForDate1.put(order1.getOrderNumber(), order1);
        orders.put(order1.getOrderDate(), ordersForDate1);

        Map<Integer, Order> ordersForDate2 = new HashMap<>();
        LocalDate date2 = LocalDate.parse("2013-02-06");

        Order order2 = new Order(date2, "Doctor Who", "WA", "Wood", new BigDecimal("243.00"), new BigDecimal("9.25"), new BigDecimal("5.15"), new BigDecimal("4.75"));
        order2.setOrderNumber(2);
        ordersForDate2.put(order2.getOrderNumber(), order2);

        Order order3 = new Order(date2, "Albert Einstein", "KY", "Carpet", new BigDecimal("217.00"), new BigDecimal("6.00"), new BigDecimal("2.25"), new BigDecimal("2.10"));
        order3.setOrderNumber(3);
        ordersForDate2.put(order3.getOrderNumber(), order3);
        orders.put(date2, ordersForDate2);

        largestOrderNumber = 3;
    }

    @Override
    public int getNextOrderNumber() {
        largestOrderNumber++;
        return largestOrderNumber;
    }

    @Override
    public Order addOrder(Order order) {
        order.setOrderNumber(largestOrderNumber + 1);
        //Create a new map if no orders exist for the date
        if (orders.get(order.getOrderDate()) == null) {
            Map<Integer, Order> orderForDate = new HashMap<>();
            orderForDate.put(order.getOrderNumber(), order);
            orders.put(order.getOrderDate(), orderForDate);
        } else {
            //Add the order to the corresponding map for the date
            orders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        }
        return orders.get(order.getOrderDate()).get(order.getOrderNumber());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        if (orders.containsKey(date) && orders.get(date) != null) {
            return orders.get(date).get(orderNumber);
        }
        return null;
    }

    @Override
    public Order editOrder(Order newOrder) {
        Order currentOrder = orders.get(newOrder.getOrderDate()).get(newOrder.getOrderNumber());
        return orders.get(currentOrder.getOrderDate()).put(newOrder.getOrderNumber(), newOrder);
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) {
        if (orders.containsKey(date)) {
            return new ArrayList<>(orders.get(date).values());
        }
        return null;
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() {
        return orders;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        if (orders.containsKey(date) && orders.get(date) != null) {
            Order removedOrder = orders.get(date).remove(orderNumber);
            return removedOrder;
        }
        return null;
    }
}
