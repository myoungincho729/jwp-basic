package core.nmvc;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping2 implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping2(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();

        Set<Method> methods = getRequestMappingMethods(controllers.keySet());

        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);

            HandlerKey handlerKey = new HandlerKey(annotation.value(), annotation.method());
            HandlerExecution handlerExecution = new HandlerExecution(
                    controllers.get(method.getDeclaringClass()),
                    method);

            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        Set<Method> reqMappingMethods = new HashSet<>();
        for (Class clazz : controllers) {
            Set<Method> allMethods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
            reqMappingMethods.addAll(allMethods);
        }
        return reqMappingMethods;
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
