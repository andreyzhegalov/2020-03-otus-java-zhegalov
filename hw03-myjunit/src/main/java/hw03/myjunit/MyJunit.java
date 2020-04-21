package hw03.myjunit;

import java.lang.reflect.Method;

public class MyJunit {
    private TestClass testClass;
    private Report report;

    public MyJunit(final Class<?> klass) {
        testClass = new TestClass(klass);
        report = new Report();
    }

    public void run() {
        for (final var test : testClass.getTestMethods()) {
            new Runner(testIstance(), testMethod(test), report).run();
        }
    }

    public String getReport() {
        return report.make();
    }

    private Object testIstance() {
        Object testIstance;
        try {
            testIstance = testClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Testing class not have public constructor");
        }
        return testIstance;
    }

    private TestMethod testMethod(final Method method) {
        return new TestMethod(method, testClass);
    }
}
