package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();


    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long questionId = Long.valueOf(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        if (!UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(user)) {
            throw new RuntimeException("작성자만 질문을 삭제할 수 있습니다.");
        }
        long countOfAnswersWroteByWriter = answers.stream()
                .filter(answer -> answer.getWriter().equals(user.getName()))
                .count();
        if (answers.size() > countOfAnswersWroteByWriter) {
            throw new RuntimeException("질문을 삭제할 수 없습니다.");
        }
        questionDao.deleteById(questionId);
        return jspView("redirect:/");
    }
}
