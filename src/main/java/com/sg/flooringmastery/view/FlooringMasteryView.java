package com.sg.flooringmastery.view;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class FlooringMasteryView {
    private final UserIO io;

    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int displayMainMenuAndGetSelection() {
        io.print("*****************************");
        io.print("FLOORING PROGRAM");
        io.print("1. Display orders for a date");
        io.print("2. Add an order");
        io.print("3. Edit an order");
        io.print("4. Remove an order");
        io.print("5. Export order data");
        io.print("6. Quit");
        io.print("*****************************");
        return io.readInt("Your selection:", 1, 6);
    }

    public LocalDate getDateInput() {
        boolean isValidDate = false;
        LocalDate date = null;
        while (!isValidDate) {
            String input = io.readString("Enter the date(ddmmyyyy): ");
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                date = LocalDate.parse(input, formatter);
                isValidDate = true;
            } catch (Exception e) {
                io.print("Invalid date. Please enter in ddmmyyyy");
            }
        }
        return date;
    }

    public void displayOrders(List<Order> ordersForDate) {

        io.print("******* ORDERS FOR DATE: " + ordersForDate.get(0).getOrderDate());

        ordersForDate.forEach(order -> {
            io.print("Order Number: " + order.getOrderNumber());
            io.print("Customer Name: " + order.getCustomerName());
            io.print("State: " + order.getState());
            io.print("Product Type:" + order.getProductType());
            io.print("Area: " + order.getArea());
            io.print("Tax rate: " + order.getTaxRate());
            io.print("Cost per square foot: " + order.getCostPerSquareFoot());
            io.print("Labour cost per square foot: " + order.getLabourCostPerSquareFoot());
            io.print("Labour Cost:" + order.getLabourCost());
            io.print("Material cost: " + order.getMaterialCost());
            io.print("Tax:" + order.getTax());
            io.print("Total: " + order.getTotal());
            io.print("");
        });

        io.readString("Press enter to go back to Main Menu.");

    }

    public void displayOrderInfo(Order order) {
        io.print("Order Date: " + order.getOrderDate());
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Product Type:" + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Tax rate: " + order.getTaxRate());
        io.print("Cost per square foot: " + order.getCostPerSquareFoot());
        io.print("Labour cost per square foot: " + order.getLabourCostPerSquareFoot());
        io.print("Labour Cost:" + order.getLabourCost());
        io.print("Material cost: " + order.getMaterialCost());
        io.print("Tax:" + order.getTax());
        io.print("Total: " + order.getTotal());
        io.print("");

    }

    public void displayAddOrderBanner() {
        io.print("***** ADD ORDER *****");
    }

    public Order getAddOrderInput(List<Tax> taxes, List<Product> products) {

        //Get and validate date input
        boolean isValidDate = false;
        LocalDate date = null;
        while (!isValidDate) {
            String input = io.readString("Enter Order Date(ddmmyyyy): ");
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                date = LocalDate.parse(input, formatter);
                if (date.isAfter(LocalDate.now())) {
                    isValidDate = true;
                } else {
                    io.print("Date must be in the future!");
                }

            } catch (Exception e) {
                io.print("Invalid date. Please enter in ddmmyyyy.");
            }

        }

        //Get and validate customer name input
        boolean isValidName = false;
        String customerName = "";
        while (!isValidName) {
            customerName = io.readString("Customer name: ");
            customerName = validateName(customerName);
            if (customerName != null) {
                isValidName = true;
            }
        }

        //Get and validate state/tax input
        boolean isValidState = false;
        String stateInput = "";
        Tax tax = null;
        while (!isValidState) {
            stateInput = io.readString("State abbreviation: ");
            tax = validateState(stateInput, taxes);
            if (tax != null) {
                isValidState = true;
            }

        }

        //Get and validate product input
        products.forEach(product -> {
            io.print("PRODUCT TYPE: " + product.getProductType());
            io.print("Product cost per sq ft:  " + product.getCostPerSquareFoot());
            io.print("Labour cost per sq ft: " + product.getLabourCostPerSquareFoot());
            io.print("");
        });

        boolean isValidProduct = false;
        String productInput = "";
        Product product = null;
        while (!isValidProduct) {
            productInput = io.readString("Product type: ");
            product = validateProduct(productInput, products);
            if (product != null)
                isValidProduct = true;
        }

        //Get and validate area input
        boolean isValidArea = false;
        BigDecimal area = null;
        while (!isValidArea) {
            String areaStr = io.readString("Area: ");
            area = validateArea(areaStr);
            if (area != null) {
                isValidArea = true;
            }

        }

        return new Order(date, customerName, stateInput, productInput,
                area, tax.getTaxRate(), product.getCostPerSquareFoot(), product.getLabourCostPerSquareFoot());
    }

    public void displayAddOrderSuccess() {
        io.print("Order successfully added!");
        io.readString("Press enter to go back to Main Menu.");
    }

    public void displayEditOrderBanner() {
        io.print("***** EDIT ORDER *****");
    }

    public int getOrderNumber() {
        return io.readInt("Enter Order Number:");
    }

    public Order getEditOrderInput(Order order, List<Tax> taxes, List<Product> products) {
        io.print("If you want to keep previous data, simply press Enter.");

        //Get and validate customer name input
        boolean isValidName = false;
        String customerName = "";
        while (!isValidName) {
            customerName = io.readString("Customer name (" + order.getCustomerName() + "): ");
            if (customerName.equals("")) {
                customerName = order.getCustomerName();
            }
            customerName = validateName(customerName);
            if (customerName != null) {
                isValidName = true;
            }
        }

        //Get and validate state/tax input
        boolean isValidState = false;
        String stateInput = "";
        Tax tax = null;
        while (!isValidState) {
            stateInput = io.readString("State abbreviation(" + order.getState() + "): ");
            if (stateInput.equals("")) {
                stateInput = order.getState();
            }
            tax = validateState(stateInput, taxes);
            if (tax != null) {
                isValidState = true;
            }

        }

        //Get and validate product input
        products.forEach(product -> {
            io.print("PRODUCT TYPE: " + product.getProductType());
            io.print("Product cost per sq ft:  " + product.getCostPerSquareFoot());
            io.print("Labour cost per sq ft: " + product.getLabourCostPerSquareFoot());
            io.print("");
        });

        boolean isValidProduct = false;
        String productInput = "";
        Product product = null;
        while (!isValidProduct) {
            productInput = io.readString("Product type(" + order.getProductType() + "): ");
            if (productInput.equals("")) {
                productInput = order.getProductType();
            }
            product = validateProduct(productInput, products);
            if (product != null)
                isValidProduct = true;
        }

        //Get and validate area input
        boolean isValidArea = false;
        BigDecimal area = null;
        while (!isValidArea) {
            String areaStr = io.readString("Area(" + order.getArea() + "): ");
            if (areaStr.equals("")) {
                area = order.getArea();
                isValidArea = true;
            } else {
                if (validateArea(areaStr) != null) {
                    isValidArea = true;
                    area = validateArea(areaStr);
                }
            }
        }
        Order newOrder = new Order(order.getOrderDate(), customerName, stateInput, productInput,
                area, tax.getTaxRate(), product.getCostPerSquareFoot(), product.getLabourCostPerSquareFoot());
        newOrder.setOrderNumber(order.getOrderNumber());
        return newOrder;
    }

    public void displayEditOrderSuccess() {
        io.print("Order successfully edited!");
        io.readString("Press enter to go back to Main Menu.");
    }

    public void displayRemoveOrderBanner() {
        io.print("***** REMOVE ORDER *****");
    }

    public boolean getConfirmation() {
        boolean isValidInput = false;
        boolean isConfirmed = false;
        while (!isValidInput) {
            String userInput = io.readString("Are you sure you want to continue? (y/n)");
            if (userInput.equals("y")) {
                isValidInput = true;
                isConfirmed = true;
            } else if (userInput.equals("n")) {
                isValidInput = true;
                isConfirmed = false;
            } else {
                io.print("Please type y or n");
            }
        }
        return isConfirmed;
    }

    public void displayRemoveOrderSuccess() {
        io.print("Order successfully removed!");
        io.readString("Press enter to go back to Main Menu.");
    }

    public void displayExportDataSuccess() {
        io.print("Data successfully exported!");
        io.readString("Press enter to go back to main menu.");
    }

    public void displayExitMessage() {
        io.print("Thank you! Good bye");
        io.print("**********************");
    }

    public void displayErrorMessage(String msg) {
        io.print("*********");
        io.print("ERROR: " + msg);
        io.readString("Press enter to go back to Main Menu.");

    }

    public void displayUnknownCommandMessage() {
        io.print("Unknown command!");
    }

    private String validateName(String nameInput) {
        if (Pattern.matches("^[a-zA-Z0-9,.\\s]+", nameInput)) {
            return nameInput = nameInput.replaceAll("[,]", "");
        } else {
            io.print("Name cannot be blank and can contain letters, numbers, commas, periods and spaces");
        }
        return null;
    }

    private Tax validateState(String stateInput, List<Tax> taxes) {
        Tax matchedTax = taxes.stream()
                .filter(t -> t.getState().equals(stateInput))
                .findFirst().orElse(null);
        if (matchedTax == null) {
            io.print("Sorry we don't sell in this region. Choose from the tax list: ");
            taxes.forEach(t -> io.print(t.getState() + " "));
        }
        return matchedTax;
    }

    private Product validateProduct(String productInput, List<Product> products) {
        Product matchedProduct = products.stream()
                .filter(p -> p.getProductType().equals(productInput))
                .findFirst()
                .orElse(null);
        if (matchedProduct == null) {
            io.print("Please type product type from the list.");
        }
        return matchedProduct;
    }

    private BigDecimal validateArea(String areaStr) {
        BigDecimal area = null;
        try {
            area = new BigDecimal(areaStr);

            if (area.compareTo(BigDecimal.valueOf(100)) >= 0) {
                return area;
            } else {
                io.print("Area must be at least 100 sq ft");
            }
        } catch (NumberFormatException e) {
            io.print("Enter a decimal value.");
        }
        return null;
    }


}
