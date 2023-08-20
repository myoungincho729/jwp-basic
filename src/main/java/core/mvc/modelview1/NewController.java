package core.mvc.modelview1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NewController {
    ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
