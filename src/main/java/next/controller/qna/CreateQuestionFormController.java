package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateQuestionFormController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(request.getSession())) {
            throw new RuntimeException("로그인 사용자만 질문할 수 있습니다.");
        }
        User user =  (User) session.getAttribute("user");
        return jspView("/qna/form.jsp").addObject("user", user);
    }
}
