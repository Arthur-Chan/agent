package org.chan;

/**
 * Hello world!
 */
public class App {

    public void doSomething(String something) {
        System.out.println("print " + something);
    }

    public static void main(String[] args) {
        new App().doSomething("Hello World");
    }
}
