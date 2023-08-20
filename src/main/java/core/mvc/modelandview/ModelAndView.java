package core.mvc.modelandview;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ModelAndView {
    private Model model;
    private View view;

    public ModelAndView(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }

    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        view.process(req, resp, model);
    }
}
