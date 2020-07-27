package hw15.generator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TriangleGeneratorTest {

    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new TriangleGenerator(0, 0));
    }

    @Test
    public void testGetNextForNullMaxValue() {
        final var generator = new TriangleGenerator(0, 0);
        assertTrue(generator.getNext().isEmpty());
    }

    @Test
    public void testGetNextForEqualStartAndMaxValue() {
        final var generator = new TriangleGenerator(3, 3);
        assertTrue(generator.getNext().isEmpty());
    }

    @Test
    public void testGetNextWhenStartAboveMax() {
        final var generator = new TriangleGenerator(3, 2);
        assertTrue(generator.getNext().isEmpty());
    }

    @Test
    public void testGetNext() {
        final var generator = new TriangleGenerator(1, 3);
        assertEquals(generator.getNext().get(), 1);
        assertEquals(generator.getNext().get(), 2);
        assertEquals(generator.getNext().get(), 3);
        assertEquals(generator.getNext().get(), 2);
        assertEquals(generator.getNext().get(), 1);
        assertTrue(generator.getNext().isEmpty());
    }
}
