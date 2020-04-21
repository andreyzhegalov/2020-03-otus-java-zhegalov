/*
 *
 */
package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.*;

public class MyJunitTest {
    @Test
    public void ctrTest() {
        final var clazz = SomeClassTest.class;
        assertDoesNotThrow(() -> {
            new MyJunit(clazz);
        });
    }

    @Test
    public void runTest() {
        final var clazz = SomeClassTest.class;
        new MyJunit(clazz).run();
    }
}
