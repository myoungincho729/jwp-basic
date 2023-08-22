package core.nmvc;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class ControllerScannerTest {
    private ControllerScanner cs;
    @Before
    public void setup() {
        cs = new ControllerScanner("core.nmvc");
    }

    @Test
    public void scannerTest() {
        Map<Class<?>, Object> controllers = cs.getControllers();
        for (Class controller : controllers.keySet()) {
            System.out.println("controller = " + controller);
            System.out.println("controllers.get(controller) = " + controllers.get(controller));
        }
    }
}