package core.ref;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();


        logger.debug("className : {}", clazz.getName());
        for (Field field : declaredFields) {
            logger.debug("fieldName : {}", field.getName());
            logger.debug("fieldType : {}", field.getType());
            logger.debug("fieldModifiers : {}", field.getModifiers());
        }
        for (Constructor constructor : declaredConstructors) {
            logger.debug("constructorName : {}", constructor.getName());
            logger.debug("constructorModifiers : {}", constructor.getModifiers());
        }
        for (Method method : declaredMethods) {
            logger.debug("methodName : {}", method.getName());
            logger.debug("methodModifiers : {}", method.getModifiers());
            logger.debug("methodReturnType : {}", method.getReturnType());
//            Class<?>[] parameterTypes = method.getParameterTypes();
//            for (Class<?> paramType : parameterTypes) {
//                paramType.
//            }
//            logger.debug("methodReturnType : {}", method.getParameterTypes());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : declaredConstructors) {
            System.out.println("constructor.getName() = " + constructor.getName());
//            Class[] parameterTypes = constructor.getParameterTypes();
//            for (Class c : parameterTypes) {
//                System.out.println(c.getName());
//            }
            Object o = constructor.newInstance("qwer", "qwer", "qwer", "qwer");
            User user = (User) o;
            System.out.println(user.toString());
        }
    }
    
    @Test
    public void privateFieldAccess() throws InstantiationException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = clazz.newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            System.out.println(field.getName());
            if (field.getName().equals("age")) {
                field.set(student, 10);
            }
            if (field.getName().equals("name")) {
                field.set(student, "myoungin");
            }
        }
        Assert.assertEquals(10, student.getAge());
        Assert.assertEquals("myoungin", student.getName());
    }
}
