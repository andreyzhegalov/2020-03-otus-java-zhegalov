package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class AtmTest {
    @Test
    public void testPutCell() {
        final var atm = new Atm();
        assertEquals(0, atm.getCellCnt());
        atm.putCell(new BanknoteCell(new Banknote(100), 100));
        assertEquals(1, atm.getCellCnt());
    }

    @Test
    public void testGetCell() {
        final var atm = new Atm();
        assertThrows(UnsupportedOperationException.class, () -> atm.getCell());
    }

    @Test
    public void testGetInitBalance() {
        assertEquals(0, new Atm().getBalance());
    }

    @Test
    public void testPutBanknotesSuccess() {
        final Banknote[] banknotes = { new Banknote(50), new Banknote(100) };
        final var atm = new Atm();
        atm.putCell(new BanknoteCell(new Banknote(50), 10));
        atm.putCell(new BanknoteCell(new Banknote(100), 10));
        atm.put(banknotes);
    }

    @Test
    public void testPutBanknotesFailed() {
        final Banknote[] banknotes = { new Banknote(50), new Banknote(100) };
        final var atm = new Atm();
        atm.putCell(new BanknoteCell(new Banknote(50), 10));
        atm.putCell(new BanknoteCell(new Banknote(200), 10));
        assertThrows(RuntimeException.class, () -> atm.put(banknotes));
    }

    private Atm prepareAtm() {
        final int cellCapacity = 2;
        final var cost50 = Stream.generate(() -> new Banknote(50)).limit(cellCapacity);
        final var cost100 = Stream.generate(() -> new Banknote(100)).limit(cellCapacity);
        final var cost200 = Stream.generate(() -> new Banknote(200)).limit(cellCapacity);
        Stream<Banknote> allCost = Stream.concat(Stream.concat(cost50, cost100), cost200);
        Banknote[] inAtm = allCost.toArray(Banknote[]::new);

        final var atm = new Atm();
        atm.putCell(new BanknoteCell(new Banknote(50), cellCapacity));
        atm.putCell(new BanknoteCell(new Banknote(100), cellCapacity));
        atm.putCell(new BanknoteCell(new Banknote(200), cellCapacity));
        atm.put(inAtm);
        return atm;
    }

    @Test
    public void testGetNoneBanknotes() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(0);
        assertEquals(0, banknotes.length);
    }

    @Test
    public void testGetBanknotesFailure() {
        final var atm = prepareAtm();
        assertThrows(RuntimeException.class, () -> atm.get(80));
    }

    @Test
    public void testGetMinimalCntBanknotesCase1() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(100);
        final Banknote[] expected = { new Banknote(100) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetMinimalCntBanknotesCase2() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(150);
        final Banknote[] expected = { new Banknote(100), new Banknote(50) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetMinimalCntBanknotesCase3() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(550);
        final Banknote[] expected = { new Banknote(200), new Banknote(200), new Banknote(100), new Banknote(50) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetAllBanknotes() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(700);
        final Banknote[] expected = { new Banknote(200), new Banknote(200), new Banknote(100), new Banknote(100),
                new Banknote(50), new Banknote(50) };
        assertArrayEquals(expected, banknotes);
    }
}
