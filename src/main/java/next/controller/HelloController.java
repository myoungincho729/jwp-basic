package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("hello hello");
        return "/index.jsp";
    }
}
