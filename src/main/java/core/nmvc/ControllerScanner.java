package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private Reflections reflections;
    private Set<Class<?>> annotated;
    private Map<Class<?>, Object> instances = new HashMap<>();

    public ControllerScanner(Object... basePackages) throws InstantiationException, IllegalAccessException {
        this.reflections = new Reflections(basePackages);
        annotated = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class clazz : annotated) {
            instances.put(clazz, clazz.newInstance());
        }
    }

    public void printControllers() {
        for (Class clazz : annotated) {
            System.out.println("clazz.getName() = " + clazz.getName());
        }
    }

    public Map<Class<?>, Object> getInstances() {
        return instances;
    }
}
