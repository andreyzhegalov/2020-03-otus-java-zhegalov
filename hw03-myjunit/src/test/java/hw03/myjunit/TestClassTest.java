/*
 *
 */
package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestClassTest {
    @Test
    public void ctrTest() {
        final var clazz = new TestHelper().getClazz();
        assertDoesNotThrow(() -> {
            new TestClass(clazz);
        });
    }

    @Test
    public void getConstructorTest() {
        final var testClass = new TestHelper().getTestClass();
        assertDoesNotThrow(() -> {
            testClass.getConstructor();
        });
    }

    @Test
    public void getTestsList() {
        final var testClass = new TestHelper().getTestClass();
        assertEquals(2, testClass.getTestMethods().size());
    }

    @Test
    public void getBeforeTest() {
        final var testClass = new TestHelper().getTestClass();
        assertEquals("beforeTest", testClass.getBefore().getName());
    }

    @Test
    public void getAfterTest() {
        final var testClass = new TestHelper().getTestClass();
        assertEquals("afterTest", testClass.getAfter().getName());
    }
}
