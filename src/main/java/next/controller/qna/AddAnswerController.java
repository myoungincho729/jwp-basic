package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.valueOf(req.getParameter("questionId"));
        Answer answer = new Answer(
                req.getParameter("writer"),
                req.getParameter("contents"),
                questionId);
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        Question question = questionDao.findById(questionId);
        question.addCountOfComments();
        questionDao.updateCountOfComments(question);
        return jsonView()
                .addObject("answer", savedAnswer)
                .addObject("question", question);
    }
}
