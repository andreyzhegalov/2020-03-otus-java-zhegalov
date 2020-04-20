/*
 *
 */
package hw03.myjunit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMethodTest {
    @Test
    public void ctrTest() {
        Class<SomeClassTest> clazz = SomeClassTest.class;
        var testClass = new TestClass( clazz );
        assertDoesNotThrow( ()-> {
            new TestMethod( testClass.getTestMethods().get(0), testClass);
        });
    }

    @Test
    public void invokeTest(){
        Class<SomeClassTest> clazz = SomeClassTest.class;
        var testClass = new TestClass( clazz );
        var testMethod = new TestMethod( testClass.getTestMethods().get(0), testClass);
        Object istance;
        try {
            istance = testClass.getConstructor().newInstance();
        } catch (Exception e) {
            return;
        }
        assertDoesNotThrow( ()-> {
            testMethod.invoke( istance );
        });

    }
}

