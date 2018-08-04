package org.chan;


import java.lang.instrument.Instrumentation;

public class AppAgent {
    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer(new PerformanceTransformer());
    }
}
