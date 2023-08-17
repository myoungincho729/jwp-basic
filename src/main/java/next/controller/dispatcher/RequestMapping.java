package next.controller.dispatcher;

import next.controller.Controller;
import next.controller.CreateUserController2;
import next.controller.HelloController;
import next.controller.HomeController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> matcher = new HashMap<>();
    static {
        matcher.put("/", new HomeController());
        matcher.put("/user/create", new CreateUserController2());
        matcher.put("/hello", new HelloController());
    }

    public static Controller findController(String uri) {
        Controller controller = matcher.get(uri);
        if (controller == null) {
            return null;
        }
        return controller;
    }
}
