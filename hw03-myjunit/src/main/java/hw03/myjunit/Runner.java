package hw03.myjunit;

public class Runner {
    private final Object testIstance;
    private TestMethod testMethod;
    private Report report;

    public Runner(Object testIstance, TestMethod method, Report report) {
        this.testIstance = testIstance;
        testMethod = method;
        this.report = report;
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
        report.addLine( "[ RUN  ] " + testMethod.getName());
        try {
            testMethod.invoke(testIstance);
            report.testSuccessfull();
            report.addLine( "[ OK   ] " + testMethod.getName());
        } catch (Throwable e) {
            report.testFailed();
            report.addLine(  e.getCause().toString() );
            report.addLine( "[ FAIL ] " + testMethod.getName());
        }
    }

    public void runBefore() throws FailedBefore {
        try {
            try {
                testMethod.getBefore().invoke(testIstance);
            } catch (NullPointerException e) {
            }
        } catch (Throwable e) {
            throw new FailedBefore();
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
