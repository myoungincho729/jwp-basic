package core.mvc.modelview1.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JspView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class UpdateFormUserController implements NewController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        Model model = new Model();
        model.setAttribute("user", user);
        JspView jspView = new JspView("/user/updateForm.jsp");
        return new ModelAndView(model, jspView);
    }
}
