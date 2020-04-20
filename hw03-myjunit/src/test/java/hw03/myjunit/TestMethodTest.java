/*
 *
 */
package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class TestMethodTest {

    private final TestClass testClass;
    private final Method failMethod;
    private final Method successMethod;
    private final Object istance;

    TestMethodTest() {
        final var helper = new TestHelper();
        testClass = helper.getTestClass();
        failMethod = helper.getFailMethod();
        successMethod = helper.getSuccessMethod();
        istance = helper.getIstance();
    }

    @Test
    public void ctrTest() {
        assertDoesNotThrow(() -> {
            new TestMethod(failMethod, testClass);
        });
    }

    @Test
    public void invokeTest() {
        var testMethod = new TestMethod(successMethod, testClass);
        assertDoesNotThrow(() -> {
            testMethod.invoke(istance);
        });
    }

    @Test
    public void invokeTestFail() {
        var testMethod = new TestMethod(failMethod, testClass);
        assertThrows(InvocationTargetException.class, () -> {
            testMethod.invoke(istance);
        });
    }
}
