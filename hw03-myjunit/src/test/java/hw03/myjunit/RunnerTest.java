/*
 *
 */

package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class RunnerTest {
    final Object testIstance;
    final TestClass testClass;
    final Method successMethod;
    final Method failMethod;

    public RunnerTest() {
        final var testHelper = new TestHelper();
        testIstance = testHelper.getIstance();
        testClass = testHelper.getTestClass();
        successMethod = testHelper.getSuccessMethod();
        failMethod = testHelper.getFailMethod();
    }

    @Test
    public void ctrTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertDoesNotThrow(() -> {
            new Runner(testIstance, testMethod);
        });
    }

    @Test
    public void runSuccessTestTest() {
        final var testMethod = new TestMethod(successMethod, testClass);
        assertDoesNotThrow(() -> {
            new Runner(testIstance, testMethod).run();
        });
    }

    @Test
    public void runFailTestTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertDoesNotThrow(() -> {
            new Runner(testIstance, testMethod).run();
        });
    }
}
