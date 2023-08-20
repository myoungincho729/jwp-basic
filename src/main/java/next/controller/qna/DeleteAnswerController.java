package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.jdbc.DataAccessException;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.valueOf(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();
        ObjectMapper mapper = new ObjectMapper();
        String returnMessage;
        try {
            answerDao.deleteByAnswerId(answerId);
            returnMessage = mapper.writeValueAsString(Result.ok());
        } catch (DataAccessException e) {
            returnMessage = mapper.writeValueAsString(Result.fail("error ..."));
        }
        PrintWriter out = resp.getWriter();
        out.print(returnMessage);
        return null;
    }
}
