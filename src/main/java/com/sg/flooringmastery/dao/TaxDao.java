package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;

import java.util.List;

public interface TaxDao {

    List<Tax> getAllTaxes() throws PersistenceException;

}
