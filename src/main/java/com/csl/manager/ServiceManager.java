package com.csl.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceManager {
    private static ApplicationContext context = new ClassPathXmlApplicationContext(
            "spring/application-context.xml");
    @SuppressWarnings("unchecked")
    public static <T> T getService(String name){
        return (T)context.getBean(name);
    }
    public static String CARDSERVICE = "cardService";
}
