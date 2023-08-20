package core.mvc.modelandview.controller.qna;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.Controller;
import core.mvc.modelandview.JsonView;
import core.mvc.modelandview.Model;
import core.mvc.modelandview.ModelAndView;
import core.mvc.modelandview.NewController;
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
