package org.chan;

public aspect DoSomethingAspect {

    pointcut callDoSomething():
            call(* org.chan.AppAop.doSomething(..));

    Object around():callDoSomething(){
        long start = System.currentTimeMillis();
        Object result = proceed();
        System.out.println("executeTime: " + (System.currentTimeMillis() - start));
        return result;
    }

}
