package org.chan;

public aspect DoSomethingAspect {

    pointcut callDoSomething():
            call(* org.chan.AppAop.doSomething(..));

    before():callDoSomething(){
        System.out.println("before-->" + thisJoinPoint);
    }

    Object around():callDoSomething(){
        long start = System.currentTimeMillis();
        Object result = proceed();
        System.out.println("executeTime: " + (System.currentTimeMillis() - start));
        return result;
    }

    after():callDoSomething(){
        System.out.println("after-->" + thisJoinPoint);
    }
}
