package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateFormQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.valueOf(request.getParameter("questionId"));
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            throw new RuntimeException("로그인 사용자만 질문을 수정할 수 있습니다.");
        }
        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionDao.findById(questionId);
        if (!question.isSameWriter(user)) {
            throw new RuntimeException("질문을 한 사용자만 수정할 수 있습니다.");
        }
        return jspView("/qna/updateForm.jsp")
                .addObject("user", user)
                .addObject("question", question);
    }
}
