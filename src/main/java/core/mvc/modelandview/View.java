package core.mvc.modelandview;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface View {
    void process(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException;
}
