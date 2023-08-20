package core.mvc.modelview1;

import core.mvc.modelview1.controller.HomeController;
import core.mvc.modelview1.controller.qna.AddAnswerController;
import core.mvc.modelview1.controller.qna.DeleteAnswerController;
import core.mvc.modelview1.controller.qna.ShowController;
import core.mvc.modelview1.controller.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NewRequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(NewDispatcherServlet.class);
    private Map<String, NewController> mappings = new HashMap<>();

    public void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());
        mappings.put("/qna/show", new ShowController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
        logger.info("Initialized Request Mapping!");
    }

    public NewController findController(String url) {
        return mappings.get(url);
    }

    void put(String url, NewController controller) {
        mappings.put(url, controller);
    }
}
