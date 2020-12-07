package com.sg.flooringmastery.dao;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class AuditDaoFileImpl implements AuditDao {

    public static final String AUDIT_FILE = "audit.txt";

    @Override
    public void writeAuditEntry(String entry) throws PersistenceException {
        try (PrintWriter out = new PrintWriter(new FileWriter(AUDIT_FILE, true))) {
            //Create timestamp
            LocalDateTime timestamp = LocalDateTime.now();
            out.println(timestamp.toString() + " : " + entry);
            out.flush();
        } catch (IOException e) {
            throw new PersistenceException("Could not persist audit information.", e);
        }
    }
}
