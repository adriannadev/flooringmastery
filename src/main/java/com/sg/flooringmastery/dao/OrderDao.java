package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    int getNextOrderNumber();

    Order addOrder(Order order) throws PersistenceException;

    Order getOrder(LocalDate date, int orderNumber);

    Order editOrder(Order newOrder) throws PersistenceException;

    List<Order> getOrdersForDate(LocalDate date);

    Map<LocalDate, Map<Integer, Order>> getAllOrders();

    Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException;
}
