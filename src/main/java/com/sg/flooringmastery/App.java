package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.flooringmastery");
        appContext.refresh();
        FlooringMasteryController controller = appContext.getBean("flooringMasteryController", FlooringMasteryController.class);
        controller.run();
    }
}
