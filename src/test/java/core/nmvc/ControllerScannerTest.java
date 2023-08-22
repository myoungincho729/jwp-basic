package core.nmvc;

import junit.framework.TestCase;
import org.junit.Test;

public class ControllerScannerTest {
    @Test
    public void scannerTest() throws InstantiationException, IllegalAccessException {
        ControllerScanner controllerScanner = new ControllerScanner("core.nmvc");
        controllerScanner.printControllers();
    }
}