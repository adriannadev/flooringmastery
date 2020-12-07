package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryService {

    int getNextOrderNumber();

    Order addOrder(Order order) throws PersistenceException;

    Order getOrder(LocalDate date, int orderNumber) throws PersistenceException, NoSuchOrderException;

    Order editOrder(Order newOrder) throws PersistenceException, NoSuchOrderException;

    List<Order> getOrdersForDate(LocalDate date) throws PersistenceException, NoSuchOrderException;

    Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException, NoSuchOrderException;

    void exportData() throws PersistenceException;

    List<Tax> getAllTaxes() throws PersistenceException;

    List<Product> getAllProducts() throws PersistenceException;

}
