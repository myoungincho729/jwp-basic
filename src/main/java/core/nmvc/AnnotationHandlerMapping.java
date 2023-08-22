package core.nmvc;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) throws InstantiationException, IllegalAccessException {
        this.basePackage = basePackage;
    }

    public void initialize() throws InstantiationException, IllegalAccessException {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> instances = controllerScanner.getInstances();
        for (Class key : instances.keySet()) {
            Predicate<AnnotatedElement> annotatedElementPredicate = ReflectionUtils.withAnnotation(RequestMapping.class);
            Set<Method> methods = ReflectionUtils.getAllMethods(key, annotatedElementPredicate);
            for (Method method : methods) {
                System.out.println(method.getName());
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                System.out.println("annotation.value() = " + annotation.value());
                System.out.println("annotation.method() = " + annotation.method());

                HandlerKey handlerKey = new HandlerKey(annotation.value(), annotation.method());
                HandlerExecution handlerExecution = new HandlerExecution(instances.get(key), method);

                handlerExecutions.put(handlerKey, handlerExecution);
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
