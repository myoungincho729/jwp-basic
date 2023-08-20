package core.mvc.modelview1.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JsonView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController implements NewController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);
        Model model = new Model();
        model.setAttribute("answer", savedAnswer);
        JsonView jsonView = new JsonView("answer");
        return new ModelAndView(model, jsonView);
    }
}
