package core.mvc.modelview1.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JspView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController implements NewController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        Model model = new Model();
        model.setAttribute("question", questionDao.findById(questionId));
        model.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
        JspView jspView = new JspView("/qna/show.jsp");
        return new ModelAndView(model, jspView);
    }
}
