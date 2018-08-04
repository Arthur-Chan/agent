package org.chan;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class PerformanceTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
            if (!cl.isInterface() && cl.getName().equals("org.chan.App")) {
                final CtBehavior[] behaviors = cl.getDeclaredBehaviors();
                for (CtBehavior behavior : behaviors) {
                    if (!behavior.isEmpty()) {
                        doMethod(behavior);
                    }
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("agent 失败 " + className + ",exception " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }

    private static void doMethod(CtBehavior behavior) throws CannotCompileException, NotFoundException {
        if ("doSomething".equalsIgnoreCase(behavior.getName())) {
            //添加局部变量，如果不同过addLocalVariable设置，在调用属性时将出现compile error: no such field: startTime
            behavior.addLocalVariable("startTime", CtClass.longType);
            behavior.insertBefore("startTime = System.currentTimeMillis();");

            final MethodInfo methodInfo = behavior.getMethodInfo();
            final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            int pos = Modifier.isStatic(behavior.getModifiers()) ? 0 : 1;

            final CtClass[] parameterTypes = behavior.getParameterTypes();
            List<String> parameterNames = new ArrayList<>();

            for (int i = 0; i < parameterTypes.length; i++) {
                parameterNames.add(parameterTypes[i].getName() + " " + attribute.variableName(i + pos));
            }

            behavior.insertAfter("System.out.print(\"methodName: "
                    + String.format("%s %s%s", methodInfo.getConstPool().getClassName(), methodInfo.getName(), "(" + String.join(",", parameterNames) + ")")
                    + "\\t\");");
            behavior.insertAfter("System.out.print(\"(\");");
            behavior.insertAfter("System.out.print($$);");
            behavior.insertAfter("System.out.print(\")\\t\");");
            behavior.insertAfter("System.out.println(\"executeTime:\" + (System.currentTimeMillis() - startTime));");
        }
    }
}
