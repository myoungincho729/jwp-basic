package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.model.User;
import next.service.QnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApiDeleteQuestionController extends AbstractController {
    private QnaService qnaService = QnaService.getInstance();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return jsonView()
                    .addObject("result", Result.fail("login failed"));
        }
        Long questionId = Long.valueOf(request.getParameter("questionId"));
        User user = UserSessionUtils.getUserFromSession(session);
        try {
            qnaService.deleteQuestion(questionId, user);
            return jsonView()
                    .addObject("result", Result.ok());
        } catch (RuntimeException e) {
            return jsonView()
                    .addObject("result", Result.fail(e.getMessage()));
        }
    }
}
