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

public class LogoutController implements NewController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        JspView jspView = new JspView("redirect:/");
        Model model = new Model();
        return new ModelAndView(model, jspView);
    }
}
