package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            throw new RuntimeException("로그인 사용자만 질문할 수 있습니다.");
        }
        Question question = new Question(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("contents")
        );
        Question savedQuestion = questionDao.insert(question);
        return jspView("redirect:/");
    }
}
