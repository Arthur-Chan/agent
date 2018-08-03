package org.chan;


import javassist.*;

import java.lang.instrument.Instrumentation;

public class AppAgent {
    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            String _className = className.replaceAll("/", ".");
            if (_className.equals("org.chan.App")) {
                System.out.println("before");
            }
            return classfileBuffer;
        });
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            String _className = className.replaceAll("/", ".");
            if (_className.equals("org.chan.App")) {
                System.out.println("after");
            }
            return classfileBuffer;
        });
    }
}
