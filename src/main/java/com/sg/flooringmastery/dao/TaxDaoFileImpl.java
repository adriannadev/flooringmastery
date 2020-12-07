package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

@Component
public class TaxDaoFileImpl implements TaxDao {

    private final String TAX_FILE;
    private final String DELIMITER = ",";

    private final Map<String, Tax> allTaxes = new HashMap<>();

    public TaxDaoFileImpl() {
        TAX_FILE = "FileData/Data/Taxes.txt";
    }

    public TaxDaoFileImpl(String fileName) {
        TAX_FILE = fileName;
    }

    @Override
    public List<Tax> getAllTaxes() throws PersistenceException {
        loadFile();
        return new ArrayList<>(allTaxes.values());
    }

    private void loadFile() throws PersistenceException {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(TAX_FILE)))) {
            String currentLine;
            String[] currentTokens;
            sc.nextLine();
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentTokens = currentLine.split(DELIMITER);
                Tax currentTax = new Tax(currentTokens[0], currentTokens[1], new BigDecimal(currentTokens[2]));
                allTaxes.put(currentTax.getState(), currentTax);
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load Taxes.txt data into memory.", e);
        }
    }
}
