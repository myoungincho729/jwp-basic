package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.service.QnaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteQuestionController extends AbstractController {
    private QnaService qnaService = QnaService.getInstance();
    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();


    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/users/loginForm");
        }
        Long questionId = Long.valueOf(request.getParameter("questionId"));
        User user = UserSessionUtils.getUserFromSession(session);
        try {
            qnaService.deleteQuestion(questionId, user);
            return jspView("redirect:/");
        } catch (RuntimeException e) {
            return jspView("show.jsp")
                    .addObject("question", questionDao.findById(questionId))
                    .addObject("answers", answerDao.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }
}
