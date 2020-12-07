package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ExportDaoFileImpl implements ExportDao {

    private final String EXPORT_FILE;
    private final String DELIMITER = ",";

    private final List<Order> ordersList = new ArrayList<>();

    public ExportDaoFileImpl() {

        EXPORT_FILE = "FileData/Backup/DataExport.txt";
    }

    @Override
    public void exportData(Map<LocalDate, Map<Integer, Order>> allOrders) throws PersistenceException {

        //Get a list of all orders from the maps
        Set<LocalDate> orderDates = allOrders.keySet();
        for (LocalDate date : orderDates) {
            Map<Integer, Order> ordersForDate = new HashMap<>(allOrders.get(date));
            Set<Integer> orderNumbers = ordersForDate.keySet();
            for (Integer orderNum : orderNumbers) {
                ordersList.add(ordersForDate.get(orderNum));
            }

        }
        writeToFile();
    }

    private void writeToFile() throws PersistenceException {
        try (PrintWriter out = new PrintWriter(new FileWriter(EXPORT_FILE))) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                    "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,Date");

            ordersList.stream().map(currentOrder -> currentOrder.getOrderNumber() + DELIMITER +
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
                    currentOrder.getTotal() + DELIMITER +
                    currentOrder.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).forEach(orderAsString -> {
                out.println(orderAsString);
                out.flush();
            });

        } catch (IOException e) {
            throw new PersistenceException("Export data could not be saved", e);
        }

    }
}
