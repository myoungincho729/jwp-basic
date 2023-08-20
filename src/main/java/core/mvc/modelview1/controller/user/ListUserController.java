package core.mvc.modelview1.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JspView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

public class ListUserController implements NewController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Model model = new Model();
        if (!UserSessionUtils.isLogined(req.getSession())) {
            JspView jspView = new JspView("redirect:/users/loginForm");
            return new ModelAndView(model, jspView);
        }

        UserDao userDao = new UserDao();
        model.setAttribute("users", userDao.findAll());
        JspView jspView = new JspView("/user/list.jsp");
        return new ModelAndView(model, jspView);
    }
}
