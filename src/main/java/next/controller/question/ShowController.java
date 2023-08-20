package next.controller.question;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        Long questionId = Long.valueOf(req.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        req.setAttribute("question", question);
        req.setAttribute("answers", answers);
        return "/qna/show.jsp";
    }
}
