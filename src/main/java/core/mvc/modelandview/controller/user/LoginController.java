package core.mvc.modelandview.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import core.mvc.modelandview.JspView;
import core.mvc.modelandview.Model;
import core.mvc.modelandview.ModelAndView;
import core.mvc.modelandview.NewController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class LoginController implements NewController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        Model model = new Model();
        JspView jspView = new JspView();
        if (user == null) {
            model.setAttribute("loginFailed", true);
            jspView.setViewName("/user/login.jsp");
        }
        else if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            jspView.setViewName("redirect:/");
        } else {
            model.setAttribute("loginFailed", true);
            jspView.setViewName("/user/login.jsp");
        }
        return new ModelAndView(model, jspView);
    }
}
