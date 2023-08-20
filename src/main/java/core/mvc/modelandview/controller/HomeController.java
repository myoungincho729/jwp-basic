package core.mvc.modelandview.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.modelandview.JspView;
import core.mvc.modelandview.Model;
import core.mvc.modelandview.ModelAndView;
import core.mvc.modelandview.NewController;
import next.dao.QuestionDao;

public class HomeController implements NewController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        JspView jspView = new JspView("home.jsp");
        Model model = new Model();
        model.setAttribute("questions", questionDao.findAll());
        return new ModelAndView(model, jspView);
    }
}
