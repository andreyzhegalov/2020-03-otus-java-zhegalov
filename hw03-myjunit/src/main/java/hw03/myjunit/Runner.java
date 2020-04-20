package hw03.myjunit;

public class Runner {
    private final Object test;
    private TestMethod testMethod;

    public Runner(Object testIstance, TestMethod method) {
        this.test = testIstance;
        testMethod = method;
    }

    public void run() {
        try {
            testMethod.invoke(test);
        } catch (Throwable e) {
        }
    }
}
