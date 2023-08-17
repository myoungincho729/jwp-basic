package next.controller.dispatcher;

import next.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        Controller controller = RequestMapping.findController(requestURI);
        if (controller == null ) {
            resp.sendRedirect("/index.jsp");
            return;
        }
        String result = null;
        try {
            result = controller.execute(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (result.startsWith("redirect:")) {
            resp.sendRedirect(result.substring(9));
        } else {
            RequestDispatcher rd = req.getRequestDispatcher(result);
            rd.forward(req, resp);
        }

    }
}
