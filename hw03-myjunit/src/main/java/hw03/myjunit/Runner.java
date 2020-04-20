package hw03.myjunit;

public class Runner {
    private final Object test;
    private TestMethod testMethod;

    public Runner(Object test, TestMethod method) {
        this.test = test;
        testMethod = method;
    }

    public void run() {
        try {
            testMethod.invoke(test);
        } catch (Throwable e) {
        }
    }
}
