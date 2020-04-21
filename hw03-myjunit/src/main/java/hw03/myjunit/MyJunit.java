package hw03.myjunit;

import java.lang.reflect.Method;

public class MyJunit {
    private TestClass testClass;
    private Report report;

    public MyJunit(Class<?> klass) {
        testClass = new TestClass(klass);
        report = new Report();
    }

    public void run() {
        for (final var test : testClass.getTestMethods()) {
            new Runner(testIstance(), testMethod(test), report).run();
        }
    }

    private Object testIstance() {
        final Object testIstance;
        try {
            testIstance = testClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Testing class not have public constructor");
        }
        return testIstance;
    }

    private TestMethod testMethod(Method method) {
        return new TestMethod(method, testClass);
    }
}
