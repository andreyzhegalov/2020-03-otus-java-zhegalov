/*
 *
 */
package hw03.myjunit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        final var clazz = new TestHelper().getClazz();
        var testClass = new TestClass(clazz);
        assertDoesNotThrow(() -> {
            testClass.getConstructor();
        });
    }
}
