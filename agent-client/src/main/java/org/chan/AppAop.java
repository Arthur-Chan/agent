package org.chan;

/**
 * Aop实现
 */
public class AppAop {

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
        new AppAop().doSomething("Hello World");
    }
}
