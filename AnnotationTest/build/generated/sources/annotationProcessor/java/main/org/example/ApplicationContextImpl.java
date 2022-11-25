package org.example;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContextImpl() {
        beans.put(org.example.MyBean2.class, new org.example.MyBean2());
        beans.put(org.example.MyBean.class, new org.example.MyBean(myBean2));

    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}


