package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ProductDaoFileImpl implements ProductDao {

    private final String PRODUCT_FILE;
    private final String DELIMITER = ",";

    private final Map<String, Product> allProducts = new HashMap<>();

    public ProductDaoFileImpl() {
        PRODUCT_FILE = "FileData/Data/Products.txt";
    }

    public ProductDaoFileImpl(String fileName) {
        PRODUCT_FILE = fileName;
    }


    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        loadFile();
        return new ArrayList<>(allProducts.values());
    }

    private void loadFile() throws PersistenceException {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)))) {
            String currentLine;
            String[] currentTokens;
            sc.nextLine();
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                Product currentProduct = new Product(currentTokens[0], new BigDecimal(currentTokens[1]), new BigDecimal(currentTokens[2]));
                allProducts.put(currentProduct.getProductType(), currentProduct);
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load Taxes.txt data into memory.", e);
        }
    }
}
