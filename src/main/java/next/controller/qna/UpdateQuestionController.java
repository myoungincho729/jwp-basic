package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.valueOf(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);

        question.updateTitle(request.getParameter("title"));
        question.updateContents(request.getParameter("contents"));
        questionDao.updateQuestion(question);
        return jspView("redirect:/");
    }
}
