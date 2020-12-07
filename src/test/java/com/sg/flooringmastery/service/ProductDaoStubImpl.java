package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoStubImpl implements ProductDao {

    private final Map<String, Product> allProducts = new HashMap<>();

    public ProductDaoStubImpl() {
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        allProducts.put(product1.getProductType(), product1);

        Product product2 = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        allProducts.put(product2.getProductType(), product2);

        Product product3 = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        allProducts.put(product3.getProductType(), product3);

        Product product4 = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        allProducts.put(product4.getProductType(), product4);

    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts.values());
    }
}
