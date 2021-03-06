package hw03.myjunit.framework.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunnerTest {
    private Object testIstance;
    private TestClass testClass;
    private Method successMethod;
    private Method failMethod;
    private Report report;

    @BeforeEach
    public void init() {
        final var testHelper = new TestHelper();
        testIstance = testHelper.getIstance();
        testClass = testHelper.getTestClass();
        successMethod = testHelper.getSuccessMethod();
        failMethod = testHelper.getFailMethod();
        report = new Report();
    }

    @Test
    public void ctrTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertDoesNotThrow(() -> {
            new Runner(testIstance, testMethod, report);
        });
    }

    @Test
    public void runSuccessTestTest() {
        System.out.println(successMethod);
        final var testMethod = new TestMethod(successMethod, testClass);
        final var runner = new Runner(testIstance, testMethod, report);
        assertDoesNotThrow(() -> {
            runner.runTest();
        });
    }

    @Test
    public void runFailTestTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        final var runner = new Runner(testIstance, testMethod, report);
        assertDoesNotThrow(() -> {
            runner.runTest();
        });
    }

    @Test
    public void runBeforeTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        final var runner = new Runner(testIstance, testMethod, report);
        assertDoesNotThrow(() -> {
            runner.runBefore();
        });
    }

    @Test
    public void runAfterTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        final var runner = new Runner(testIstance, testMethod, report);
        assertDoesNotThrow(() -> {
            runner.runAfter();
        });
    }

    @Test
    public void runTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        final var runner = new Runner(testIstance, testMethod, report);
        assertDoesNotThrow(() -> {
            runner.run();
        });
    }

}
