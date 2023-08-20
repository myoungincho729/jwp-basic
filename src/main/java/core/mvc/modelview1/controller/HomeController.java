package core.mvc.modelview1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.modelview1.JspView;
import core.mvc.modelview1.Model;
import core.mvc.modelview1.ModelAndView;
import core.mvc.modelview1.NewController;
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
