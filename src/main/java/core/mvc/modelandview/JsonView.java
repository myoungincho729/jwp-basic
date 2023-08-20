package core.mvc.modelandview;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonView implements View {
    private String key;

    public JsonView(String key) {
        this.key = key;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        Object attribute = model.getAttribute(key);
        String jsonString = mapper.writeValueAsString(attribute);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
    }
}
