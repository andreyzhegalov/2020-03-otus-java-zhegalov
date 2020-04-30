package hw03.myjunit.framework.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMethodTest {

    private TestClass testClass;
    private Method failMethod;
    private Method successMethod;
    private Object istance;

    @BeforeEach
    public void init(){
        final var testHelper = new TestHelper();
        testClass = testHelper.getTestClass();
        failMethod = testHelper.getFailMethod();
        successMethod = testHelper.getSuccessMethod();
        istance = testHelper.getIstance();
    }

    @Test
    public void ctrTest() {
        assertDoesNotThrow(() -> {
            new TestMethod(failMethod, testClass);
        });
    }

    @Test
    public void invokeTest() {
        final var testMethod = new TestMethod(successMethod, testClass);
        assertDoesNotThrow(() -> {
            testMethod.invoke(istance);
        });
    }

    @Test
    public void invokeTestFail() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertThrows(InvocationTargetException.class, () -> {
            testMethod.invoke(istance);
        });
    }

    @Test
    public void getBeforeTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertNotNull(testMethod.getBefore());
    }

    @Test
    public void getAfterTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertNotNull(testMethod.getAfter());
    }

    @Test
    public void getNameTest() {
        final var testMethod = new TestMethod(failMethod, testClass);
        assertEquals("failTest", testMethod.getName());
    }
}
