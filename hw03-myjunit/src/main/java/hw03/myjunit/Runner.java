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
            runBefore();
            runTest();
        } catch (FailedBefore e) {
            throw new RuntimeException("Failed before");
        } catch (Exception e) {
            throw new RuntimeException("test should never throw an exception to this level");
        } finally {
            runAfter();
        }
    }

    public void runTest() {
        try {
            testMethod.invoke(test);
        } catch (Throwable e) {
            //TODO add result handler
        }
    }

    public void runBefore() throws FailedBefore {
        try {
            try {
                testMethod.getBefore().invoke(test);
            } catch (NullPointerException e) {
            }
        } catch (Throwable e) {
            throw new FailedBefore();
        }
    }

    public void runAfter() {
        try {
            try {
                testMethod.getAfter().invoke(test);
            } catch (NullPointerException e) {
            }
        } catch (Throwable e) {
            throw new RuntimeException("Failed after. Reason: " + e);
        }
    }
}
