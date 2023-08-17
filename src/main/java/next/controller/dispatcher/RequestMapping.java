package next.controller.dispatcher;

import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static Map<String, Controller> matcher = new HashMap<>();

    public void initMapping() {
        matcher.put("/", new HomeController());
        matcher.put("/users/form", new ForwardController("/user/form.jsp"));
        matcher.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        matcher.put("/users/create", new CreateUserController());
        matcher.put("/users/list", new ListUserController());
        matcher.put("/users/login", new LoginController());
        matcher.put("/users/logout", new LogoutController());
        matcher.put("/users/profile", new ProfileController());
        matcher.put("/users/updateForm", new ForwardController("/user/updateForm.jsp"));
        matcher.put("/users/update", new UpdateUserController());

        logger.info("Request Mapping initialized!");
    }

    public static Controller findController(String uri) {
        Controller controller = matcher.get(uri);
        if (controller == null) {
            return null;
        }
        return controller;
    }
}
