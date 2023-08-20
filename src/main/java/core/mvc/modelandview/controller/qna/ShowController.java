package core.mvc.modelandview.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.modelandview.JspView;
import core.mvc.modelandview.Model;
import core.mvc.modelandview.ModelAndView;
import core.mvc.modelandview.NewController;
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
