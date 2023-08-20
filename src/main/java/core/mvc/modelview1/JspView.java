package core.mvc.modelview1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private String viewName;
    public JspView() {

    }

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        model.putAttributesToReq(req);
        move(viewName, req, resp);
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
