package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BanknoteTest {
    @Test
    public void testGetPossibleVals() {
        assertEquals(6, Banknote.getPossibleVals().size());
    }

    @Test
    public void testCtrSuccess() {
        assertDoesNotThrow(() -> new Banknote(50));
    }

    @Test
    public void testCtrWithThrowException() {
        assertThrows(RuntimeException.class, () -> new Banknote(60));
    }

    @Test
    public void testCost() {
        assertEquals(50, new Banknote(50).getCost());
    }

    @Test
    public void testEq() {
        assertEquals(new Banknote(50), new Banknote(50));
    }

    @Test
    public void testNotEq() {
        assertNotEquals(new Banknote(100), new Banknote(50));
    }
}
