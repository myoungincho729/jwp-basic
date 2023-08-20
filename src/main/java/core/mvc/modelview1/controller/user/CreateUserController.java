package core.mvc.modelview1.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JspView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.UserDao;
import next.model.User;

public class CreateUserController implements NewController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        UserDao userDao = new UserDao();
        userDao.insert(user);

        Model model = new Model();
        JspView jspView = new JspView("redirect:/");
        return new ModelAndView(model, jspView);
    }
}
