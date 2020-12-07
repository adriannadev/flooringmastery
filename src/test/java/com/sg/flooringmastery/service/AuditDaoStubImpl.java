package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.AuditDao;
import org.springframework.stereotype.Component;

@Component
public class AuditDaoStubImpl implements AuditDao {
    @Override
    public void writeAuditEntry(String entry) {

    }
}
