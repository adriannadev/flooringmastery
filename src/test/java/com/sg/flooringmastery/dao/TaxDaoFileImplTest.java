package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaxDaoFileImplTest {

    private TaxDao testDao;

    @BeforeEach
    void setUp() throws Exception {
        String testFile = "FileData/TestFiles/TaxesTest.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(testFile))) {
            out.println("State,StateName,TaxRate\n" +
                    "TX,Texas,4.45\n" +
                    "WA,Washington,9.25\n" +
                    "KY,Kentucky,6.00\n" +
                    "CA,California,25.00");
            out.flush();
        } catch (IOException e) {
            throw new PersistenceException(
                    "Could not save Tax test data into file", e);
        }
        testDao = new TaxDaoFileImpl(testFile);
    }

    @Test
    public void testGetAllTaxes() throws Exception {
        Tax tax1 = new Tax("TX", "Texas", new BigDecimal("4.45"));
        Tax tax2 = new Tax("WA", "Washington", new BigDecimal("9.25"));
        Tax tax3 = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        Tax tax4 = new Tax("CA", "California", new BigDecimal("25.00"));

        List<Tax> retrievedTaxes = testDao.getAllTaxes();

        assertEquals(4, retrievedTaxes.size());
        assertTrue(retrievedTaxes.contains(tax1));
        assertTrue(retrievedTaxes.contains(tax2));
        assertTrue(retrievedTaxes.contains(tax3));
        assertTrue(retrievedTaxes.contains(tax4));
    }
}