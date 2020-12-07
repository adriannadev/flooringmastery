package com.sg.flooringmastery.dao;

public interface AuditDao {
    void writeAuditEntry(String entry) throws PersistenceException;
}
