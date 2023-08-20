package core.mvc.modelview1.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JsonView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController implements NewController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();

        Model model = new Model();
        try {
            answerDao.delete(answerId);
            model.setAttribute("result", Result.ok());

        } catch (Exception e) {
            model.setAttribute("result", Result.fail("error ..."));
        }

        JsonView jsonView = new JsonView("result");

        return new ModelAndView(model, jsonView);
    }
}
