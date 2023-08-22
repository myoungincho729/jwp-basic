package core.ref;

public class Junit4Test {
    public Junit4Test() {
        System.out.println("constructor call...");
    }

    @MyTest
    public void one() throws Exception {
        System.out.println("Running Test1");
    }

    @MyTest
    public void two() throws Exception {
        System.out.println("Running Test2");
    }

    public void testThree() throws Exception {
        System.out.println("Running Test3");
    }
}
