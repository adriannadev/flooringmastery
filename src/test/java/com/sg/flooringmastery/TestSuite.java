package com.sg.flooringmastery;

import com.sg.flooringmastery.dao.OrderDaoFileImplTest;
import com.sg.flooringmastery.dao.ProductDaoFileImplTest;
import com.sg.flooringmastery.dao.TaxDaoFileImplTest;
import com.sg.flooringmastery.service.FlooringMasteryServiceImplTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        FlooringMasteryServiceImplTest.class,
        OrderDaoFileImplTest.class,
        ProductDaoFileImplTest.class,
        TaxDaoFileImplTest.class,
})
public class TestSuite {
}
