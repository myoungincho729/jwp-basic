package core.mvc.modelandview.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.modelandview.JspView;
import core.mvc.modelandview.Model;
import core.mvc.modelandview.ModelAndView;
import core.mvc.modelandview.NewController;
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
