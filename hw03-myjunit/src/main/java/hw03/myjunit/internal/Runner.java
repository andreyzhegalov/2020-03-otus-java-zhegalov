package hw03.myjunit.internal;

public class Runner {
    private final Object testIstance;
    private final TestMethod testMethod;
    private final Report report;

    public Runner(final Object testIstance, final TestMethod method, final Report report) {
        this.testIstance = testIstance;
        this.testMethod = method;
        this.report = report;
    }

    public void run() {
        try {
            runBefore();
            runTest();
        } catch (BeforeEachTestFailedException e) {
            throw new RuntimeException("Failed before");
        } catch (Exception e) {
            throw new RuntimeException("test should never throw an exception to this level");
        } finally {
            runAfter();
        }
    }

    public void runTest() {
        report.addLine("[ RUN  ] " + testMethod.getName());
        try {
            testMethod.invoke(testIstance);
            report.testSuccessfull();
            report.addLine("[ OK   ] " + testMethod.getName());
        } catch (Throwable e) {
            report.testFailed();
            report.addLine(e.getCause().toString());
            report.addLine("[ FAIL ] " + testMethod.getName());
        }
    }

    public void runBefore() throws BeforeEachTestFailedException {
        try {
            try {
                testMethod.getBefore().invoke(testIstance);
            } catch (NullPointerException e) {
            }
        } catch (Throwable e) {
            throw new BeforeEachTestFailedException();
        }
    }

    public void runAfter() {
        try {
            try {
                testMethod.getAfter().invoke(testIstance);
            } catch (NullPointerException e) {
            }
        } catch (Throwable e) {
            throw new RuntimeException("Failed after. Reason: " + e);
        }
    }
}
