package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            boolean annotationPresent = method.isAnnotationPresent(MyTest.class);
            if (annotationPresent == true) {
                method.setAccessible(true);
                method.invoke(clazz.newInstance());
            }
        }
    }
}
