package hw07.model.atm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BanknoteNominalTest {
    @Test
    public void testGetPossibleVals() {
        assertEquals(6, BanknoteNominal.getPossibleVals().size());
    }

    @Test
    public void testCtrSuccess() {
        assertDoesNotThrow(() -> new BanknoteNominal(50));
    }

    @Test
    public void testCtrWithThrowException() {
        assertThrows(AtmException.class, () -> new BanknoteNominal(60));
    }

    @Test
    public void testCost() {
        assertEquals(50, new BanknoteNominal(50).getCost());
    }

    @Test
    public void testEq() {
        assertEquals(new BanknoteNominal(50), new BanknoteNominal(50));
    }

    @Test
    public void testNotEq() {
        assertNotEquals(new BanknoteNominal(100), new BanknoteNominal(50));
    }

}
