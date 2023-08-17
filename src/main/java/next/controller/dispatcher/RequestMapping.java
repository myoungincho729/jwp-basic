package next.controller.dispatcher;

import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> matcher = new HashMap<>();
    static {
        matcher.put("/", new HomeController());
        matcher.put("/users/form", new ForwardController("/user/form.jsp"));
        matcher.put("/users/create", new CreateUserController());
    }

    public static Controller findController(String uri) {
        Controller controller = matcher.get(uri);
        if (controller == null) {
            return null;
        }
        return controller;
    }
}
