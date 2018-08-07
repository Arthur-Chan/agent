package org.chan;

/**
 * 直接修改代码
 */
public class AppCode {

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
        long start = System.currentTimeMillis();
        new AppCode().doSomething("Hello World");
        System.out.println("executeTime: " + (System.currentTimeMillis() - start));
    }
}
