package next.controller.dispatcher;

import next.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private String forwardString;

    public ForwardController(String forwardString) {
        this.forwardString = forwardString;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forwardString;
    }
}
