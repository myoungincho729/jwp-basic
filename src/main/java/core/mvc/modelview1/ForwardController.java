package core.mvc.modelview1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements NewController {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
        }
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View jspView = new JspView(forwardUrl);
        Model model = new Model();
        return new ModelAndView(model, jspView);
    }
}
