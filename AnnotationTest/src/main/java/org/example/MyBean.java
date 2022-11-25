package org.example;

@Component
public class MyBean {

    private final MyBean2 myBean2;

    public MyBean(MyBean2 myBean2) {
        this.myBean2 = myBean2;
    }

    public void foo(){
        System.out.println("Hi");
    }
}
