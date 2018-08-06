package org.chan;

public class App {

    public void doSomething(String something) {
        System.out.println("do " + something);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do completely");
    }

    public static void main(String[] args) {
        new App().doSomething("Hello World");
    }
}
