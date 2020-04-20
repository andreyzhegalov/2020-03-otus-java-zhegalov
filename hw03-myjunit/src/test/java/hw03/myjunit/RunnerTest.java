/*
 *
 */

package hw03.myjunit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunnerTest {
    @Test
    public void ctrTest(){
        Class<SomeClassTest> clazz = SomeClassTest.class;
        var testClass = new TestClass( clazz );
        TestMethod testMethod = new TestMethod( testClass.getTestMethods().get(0), testClass );

        Object test;
        try {
            test = testClass.getConstructor().newInstance();
        } catch (Exception e) {
            return;
        }

        assertDoesNotThrow( ()->{
            new Runner( test, testMethod );
        });
    }

    @Test
    public void runTestTest(){
        Class<SomeClassTest> clazz = SomeClassTest.class;
        var testClass = new TestClass( clazz );
        TestMethod testMethod = new TestMethod( testClass.getTestMethods().get(0), testClass );

        Object test;
        try {
            test = testClass.getConstructor().newInstance();
        } catch (Exception e) {
            return;
        }

        assertDoesNotThrow( ()->{
            new Runner( test, testMethod ).run();
        });
    }
}

