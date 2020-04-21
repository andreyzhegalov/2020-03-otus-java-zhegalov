package hw03.myjunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AssertTest {
    @Test
    public void failTest() {
        assertThrows(AssertionError.class, () -> {
            Assert.fail(null);
        });
    }

    @Test
    public void assertTrueTest() {
        assertDoesNotThrow(() -> {
            Assert.assertTrue(true);
        });
    }

    @Test
    public void assertTrueTestFail() {
        assertThrows(AssertionError.class, () -> {
            Assert.assertTrue(false);
        });
    }
}
