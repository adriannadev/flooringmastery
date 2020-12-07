package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.service.NoSuchOrderException;
import com.sg.flooringmastery.view.FlooringMasteryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasteryController {
    private final FlooringMasteryView view;
    private final FlooringMasteryService service;

    @Autowired
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean isContinue = true;
        try {
            while (isContinue) {
                int menuChoice = getMenuSelection();
                switch (menuChoice) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        isContinue = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());

        }
    }

    private int getMenuSelection() {
        return view.displayMainMenuAndGetSelection();
    }

    private void displayOrders() throws PersistenceException {
        LocalDate chosenDate = view.getDateInput();
        try {
            List<Order> foundOrders = service.getOrdersForDate(chosenDate);
            view.displayOrders(foundOrders);
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void addOrder() throws PersistenceException {
        view.displayAddOrderBanner();
        List<Tax> taxes = service.getAllTaxes();
        List<Product> products = service.getAllProducts();
        Order newOrder = view.getAddOrderInput(taxes, products);
        newOrder.setOrderNumber(service.getNextOrderNumber());
        view.displayOrderInfo(newOrder);
        if (view.getConfirmation()) {
            service.addOrder(newOrder);
            view.displayAddOrderSuccess();
        } else {
            return;
        }
    }

    private void editOrder() throws PersistenceException {
        view.displayEditOrderBanner();
        LocalDate date = view.getDateInput();
        int orderNum = view.getOrderNumber();
        List<Tax> taxes = service.getAllTaxes();
        List<Product> products = service.getAllProducts();
        try {
            Order foundOrder = service.getOrder(date, orderNum);
            Order newOrder = view.getEditOrderInput(foundOrder, taxes, products);

            view.displayOrderInfo(newOrder);
            if (view.getConfirmation()) {
                service.editOrder(newOrder);
            } else {
                return;
            }
            view.displayEditOrderSuccess();
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() throws PersistenceException {
        view.displayRemoveOrderBanner();
        LocalDate date = view.getDateInput();
        int orderNum = view.getOrderNumber();
        try {
            Order foundOrder = service.getOrder(date, orderNum);
            view.displayOrderInfo(foundOrder);
            if (view.getConfirmation()) {
                service.removeOrder(date, orderNum);
            } else {
                return;
            }
            view.displayRemoveOrderSuccess();
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exportData() throws PersistenceException {
        service.exportData();
        view.displayExportDataSuccess();
    }

    private void unknownCommand() {
        view.displayUnknownCommandMessage();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }
}
