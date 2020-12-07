package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class OrderDaoFileImpl implements OrderDao {

    private final String ORDER_FOLDER;
    private final String DELIMITER = ",";

    private int largestOrderNumber = 0;
    private final Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

    public OrderDaoFileImpl() throws PersistenceException {
        ORDER_FOLDER = "FileData/Orders/";
        loadFile();
    }

    public OrderDaoFileImpl(String fileName) throws PersistenceException {
        ORDER_FOLDER = fileName;
        loadFile();
    }

    @Override
    public int getNextOrderNumber() {
        return largestOrderNumber + 1;
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        largestOrderNumber = order.getOrderNumber();
        //Create a new map if no orders exist for the date
        if (orders.get(order.getOrderDate()) == null) {
            Map<Integer, Order> orderForDate = new HashMap<>();
            orderForDate.put(order.getOrderNumber(), order);
            orders.put(order.getOrderDate(), orderForDate);
        } else {
            //Add the order to the corresponding map for the date
            orders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        }
        writeToFile();
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
    public Order editOrder(Order newOrder) throws PersistenceException {
        Order currentOrder = orders.get(newOrder.getOrderDate()).get(newOrder.getOrderNumber());
        Order editedOrder = orders.get(currentOrder.getOrderDate()).put(newOrder.getOrderNumber(), newOrder);
        writeToFile();
        return editedOrder;
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
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        if (orders.containsKey(date) && orders.get(date) != null) {
            Order removedOrder = orders.get(date).remove(orderNumber);
            if (removedOrder.getOrderNumber() == largestOrderNumber) {
                largestOrderNumber--;
            }
            writeToFile();
            return removedOrder;
        }
        return null;
    }

    private void loadFile() throws PersistenceException {

        //Read from all files in the Order directory
        File dataFolder = new File(ORDER_FOLDER);
        File[] fileList = dataFolder.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {
                //Try with resources
                try (Scanner sc = new Scanner(new BufferedReader(new FileReader(file)))) {
                    String currentLine;
                    String[] currentTokens;
                    sc.nextLine();
                    while (sc.hasNextLine()) {
                        currentLine = sc.nextLine();
                        currentTokens = currentLine.split(DELIMITER);
                        //Use regex to remove non-numbers
                        String dateString = file.getName().replaceAll("[^0-9]", "");
                        LocalDate orderDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("ddMMyyyy"));
                        Order currentOrder = new Order(Integer.parseInt(currentTokens[0]), orderDate,
                                currentTokens[1], currentTokens[2],
                                new BigDecimal(currentTokens[3]), currentTokens[4],
                                new BigDecimal(currentTokens[5]), new BigDecimal(currentTokens[6]),
                                new BigDecimal(currentTokens[7]), new BigDecimal(currentTokens[8]),
                                new BigDecimal(currentTokens[9]), new BigDecimal(currentTokens[10]),
                                new BigDecimal(currentTokens[11]));

                        if (orders.get(orderDate) == null) {
                            Map<Integer, Order> orderForDate = new HashMap<>();
                            orderForDate.put(currentOrder.getOrderNumber(), currentOrder);
                            orders.put(orderDate, orderForDate);
                        } else {
                            orders.get(orderDate).put(currentOrder.getOrderNumber(), currentOrder);
                        }

                        if (currentOrder.getOrderNumber() > largestOrderNumber) {
                            largestOrderNumber = currentOrder.getOrderNumber();
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new PersistenceException("Could not load Orders data into memory.", e);
                }
            }
        }

    }

    private void writeToFile() throws PersistenceException {

        //Delete the existing files from the directory
        File dataFolder = new File(ORDER_FOLDER);
        File[] fileList = dataFolder.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {
                file.delete();
            }
        }

        //Create a new file for each date
        Set<LocalDate> allDates = orders.keySet();

        for (LocalDate date : allDates) {
            List<Order> allOrders = new ArrayList<>(orders.get(date).values());

            //Try with resources
            try (PrintWriter out = new PrintWriter(new FileWriter(ORDER_FOLDER + "/Orders_" + date.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".txt"))) {
                //Print header
                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
                allOrders.stream().map(currentOrder -> currentOrder.getOrderNumber() + DELIMITER +
                        currentOrder.getCustomerName() + DELIMITER +
                        currentOrder.getState() + DELIMITER +
                        currentOrder.getTaxRate() + DELIMITER +
                        currentOrder.getProductType() + DELIMITER +
                        currentOrder.getArea() + DELIMITER +
                        currentOrder.getCostPerSquareFoot() + DELIMITER +
                        currentOrder.getLabourCostPerSquareFoot() + DELIMITER +
                        currentOrder.getMaterialCost() + DELIMITER +
                        currentOrder.getLabourCost() + DELIMITER +
                        currentOrder.getTax() + DELIMITER +
                        currentOrder.getTotal()).forEach(orderAsString -> {
                    out.println(orderAsString);
                    out.flush();
                });
            } catch (IOException e) {
                throw new PersistenceException(
                        "Could not save data into files", e);
            }
        }

    }
}
