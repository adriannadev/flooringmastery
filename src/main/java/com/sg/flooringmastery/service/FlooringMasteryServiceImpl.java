package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.*;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    private final AuditDao auditDao;
    private final ExportDao exportDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TaxDao taxDao;

    @Autowired
    public FlooringMasteryServiceImpl(AuditDao auditDao, ExportDao exportDao, OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.auditDao = auditDao;
        this.exportDao = exportDao;
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public int getNextOrderNumber() {
        return orderDao.getNextOrderNumber();
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        Order newOrder = orderDao.addOrder(order);
        writeToAudit("Order number " + newOrder.getOrderNumber() + " added.");
        return newOrder;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws NoSuchOrderException {
        Order foundOrder = orderDao.getOrder(date, orderNumber);
        if (foundOrder == null) {
            throw new NoSuchOrderException("No such order exists.");
        }
        return foundOrder;

    }

    @Override
    public Order editOrder(Order newOrder) throws PersistenceException {
        Order editedOrder = orderDao.editOrder(newOrder);
        writeToAudit("Order number " + editedOrder.getOrderNumber() + " edited.");
        return editedOrder;
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws NoSuchOrderException {
        List<Order> foundOrders = orderDao.getOrdersForDate(date);
        if (foundOrders == null || foundOrders.isEmpty()) {
            throw new NoSuchOrderException("No orders exist for date " + date);
        }
        return foundOrders;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        Order removedOrder = orderDao.removeOrder(date, orderNumber);
        writeToAudit("Order number " + removedOrder.getOrderNumber() + " removed.");
        return removedOrder;
    }

    @Override
    public void exportData() throws PersistenceException {
        exportDao.exportData(orderDao.getAllOrders());
        writeToAudit("Data exported to backup.");
    }

    @Override
    public List<Tax> getAllTaxes() throws PersistenceException {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        return productDao.getAllProducts();
    }

    private void writeToAudit(String entry) throws PersistenceException {
        auditDao.writeAuditEntry(entry);
    }
}
