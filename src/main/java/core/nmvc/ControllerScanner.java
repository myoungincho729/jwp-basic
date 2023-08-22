package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
    private Reflections reflections;

    public ControllerScanner(Object... basePackages) {
        this.reflections = new Reflections(basePackages);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        Map<Class<?>, Object> instances = new HashMap<>();
        try {
            for (Class clazz : annotated) {
                instances.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return instances;
    }
}
