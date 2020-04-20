/*
 *
 */
package hw03.myjunit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestClassTest {
    @Test
    public void ctrTest() {
        Class<SomeClassTest> clazz = SomeClassTest.class;
        assertDoesNotThrow(() -> {
            new TestClass(clazz);
        });
    }

    @Test
    public void getTestMethodsTest() {
        Class<SomeClassTest> clazz = SomeClassTest.class;
        var testClass = new TestClass(clazz);
        assertEquals(1, testClass.getTestMethods().size());
    }

    @Test
    public void getConstructorTest(){
        Class<SomeClassTest> clazz = SomeClassTest.class;
        var testClass = new TestClass( clazz );
        assertDoesNotThrow(() -> {
            testClass.getConstructor();
        });
    }
}
