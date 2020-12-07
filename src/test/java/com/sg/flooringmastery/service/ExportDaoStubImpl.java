package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ExportDao;
import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ExportDaoStubImpl implements ExportDao {
    @Override
    public void exportData(Map<LocalDate, Map<Integer, Order>> allOrders) {

    }
}
