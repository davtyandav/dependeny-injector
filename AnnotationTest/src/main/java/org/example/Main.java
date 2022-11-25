package org.example;

import org.example.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ApplicationContext applicationContext = new ApplicationContextImpl();
        applicationContext.getBean(MyBean.class).foo();
        applicationContext.getBean(MyBean2.class).foo();

    }
}