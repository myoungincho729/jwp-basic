package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        System.out.println("clazzName = " + clazz.getName());
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods) {
            String methodName = method.getName();
            if (methodName.startsWith("test")) {
                method.setAccessible(true);
                method.invoke(clazz.newInstance());
            }
        }
    }
}
