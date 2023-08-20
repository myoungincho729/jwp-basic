package core.mvc.modelview1.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.modelview1.JspView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
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
