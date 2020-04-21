/*
 *
 */
package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void getBefore() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertNotNull( testMethod.getBefore());
    }

    @Test
    public void getAfter() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertNotNull( testMethod.getAfter());
    }

    @Test
    public void getNameTest(){
        final var testMethod = new TestMethod(failMethod, testClass);
        assertEquals("failTest", testMethod.getName());
    }
}
