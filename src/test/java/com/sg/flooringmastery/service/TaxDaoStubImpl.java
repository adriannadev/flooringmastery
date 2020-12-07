package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.model.Tax;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaxDaoStubImpl implements TaxDao {

    private final Map<String, Tax> allTaxes = new HashMap<>();

    public TaxDaoStubImpl() {
        Tax tax1 = new Tax("TX", "Texas", new BigDecimal("4.45"));
        allTaxes.put(tax1.getState(), tax1);
        Tax tax2 = new Tax("WA", "Washington", new BigDecimal("9.25"));
        allTaxes.put(tax2.getState(), tax2);
        Tax tax3 = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        allTaxes.put(tax3.getState(), tax3);
        Tax tax4 = new Tax("CA", "California", new BigDecimal("25.00"));
        allTaxes.put(tax4.getState(), tax4);

    }

    @Override
    public List<Tax> getAllTaxes() {
        return new ArrayList<>(allTaxes.values());
    }
}
