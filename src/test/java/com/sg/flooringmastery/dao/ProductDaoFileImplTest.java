package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductDaoFileImplTest {

    private ProductDao testDao;

    @BeforeEach
    void setUp() throws Exception {
        String testFile = "FileData/TestFiles/ProductsTest.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(testFile))) {
            out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot\n" +
                    "Carpet,2.25,2.10\n" +
                    "Laminate,1.75,2.10\n" +
                    "Tile,3.50,4.15\n" +
                    "Wood,5.15,4.75");
            out.flush();
        } catch (IOException e) {
            throw new PersistenceException(
                    "Could not save Product test data into file", e);
        }
        testDao = new ProductDaoFileImpl(testFile);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        Product product2 = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        Product product3 = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        Product product4 = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));

        List<Product> retrievedProducts = testDao.getAllProducts();
        assertEquals(4, retrievedProducts.size());
        assertTrue(retrievedProducts.contains(product1), product1.toString());
        assertTrue(retrievedProducts.contains(product2));
        assertTrue(retrievedProducts.contains(product3));
        assertTrue(retrievedProducts.contains(product4));
    }
}