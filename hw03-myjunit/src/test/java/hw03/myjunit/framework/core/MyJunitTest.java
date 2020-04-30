package hw03.myjunit.framework.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

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
        assertDoesNotThrow(() -> {
            new MyJunit(clazz).run();
        });
    }

    @Test
    public void getReportTest() {
        final var clazz = SomeClassTest.class;
        final var myjunit = new MyJunit(clazz);
        myjunit.run();
        assertFalse(myjunit.getReport().isEmpty());
    }
}
