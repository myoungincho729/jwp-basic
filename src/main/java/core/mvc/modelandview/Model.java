package core.mvc.modelandview;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void putAttributesToReq(HttpServletRequest req) {
        for (String key : attributes.keySet()) {
            req.setAttribute(key, attributes.get(key));
        }
    }
}
