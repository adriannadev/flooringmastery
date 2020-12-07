package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts() throws PersistenceException;

}
