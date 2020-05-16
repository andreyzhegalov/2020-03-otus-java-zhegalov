package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AtmTest {

    @Test
    public void testPutMoney() {
        final Money[] moneys = { new Money(50), new Money(100) };
        final var atm = new Atm();
        atm.put(moneys);
    }

    @Test
    public void testGetInitBalance() {
        assertEquals(0, new Atm().getBalance());
    }

    @Test
    public void testPutCell() {
        final var atm = new Atm();
        assertEquals(0, atm.getCellCnt());
        atm.putCell(new MoneyCell(new Money(100), 100));
        assertEquals(1, atm.getCellCnt());
    }

    @Test
    public void testGetCell() {
        final var atm = new Atm();
        assertThrows(UnsupportedOperationException.class, () -> atm.getCell());
    }

    // @Test
    // public void testBalance() {
    // final Money[] moneys = {new Money(50), new Money(100)};
    // final var atm=new Atm();
    // atm.put(moneys);
    // assertEquals(150, atm.getBalance());
    // }

}
