package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MoneyTest {
    @Test
    public void testGetPossibleVals() {
        assertEquals(6, Money.getPossibleVals().size());
    }

    @Test
    public void testCtrSuccess() {
        assertDoesNotThrow(() -> new Money(50));
    }

    @Test
    public void testCtrWithThrowException() {
        assertThrows(RuntimeException.class, () -> new Money(60));
    }

    @Test
    public void testCost(){
        assertEquals(50, new Money(50).cost());
    }

    @Test
    public void testEqMoney(){
        assertEquals(new Money(50), new Money(50));
    }

    @Test
    public void testNotEqMoney(){
        assertNotEquals(new Money(100), new Money(50));
    }
}
