package org.example;

public interface ApplicationContext {

    <T> T getBean(Class<T> clazz);
}
