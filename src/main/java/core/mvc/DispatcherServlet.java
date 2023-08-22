package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping2;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyHandlerMapping lhm;
    private AnnotationHandlerMapping2 ahm;
    private List<HandlerMapping> mappings = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        lhm = new LegacyHandlerMapping();
        lhm.initMapping();

        ahm = new AnnotationHandlerMapping2("next.controller");
        ahm.initialize();

        mappings.add(lhm);
        mappings.add(ahm);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = getHandler(req);
        if (handler == null) {
            throw new IllegalArgumentException("존재하지 않는 URL입니다");
        }

        try {
            ModelAndView mav = execute(handler, req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (handler instanceof Controller) {
            Controller controller = (Controller) handler;
            return controller.execute(req, resp);
        } else {
            HandlerExecution controller = (HandlerExecution) handler;
            return controller.handle(req, resp);
        }
    }

    private Object getHandler(HttpServletRequest req) {
        for (HandlerMapping mapping : mappings) {
            Object handler = mapping.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }
}
