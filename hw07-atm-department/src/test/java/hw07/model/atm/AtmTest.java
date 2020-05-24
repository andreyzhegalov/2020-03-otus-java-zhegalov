package hw07.model.atm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class AtmTest {
    @Test
    public void testPutCell() {
        final var atm = new Atm();
        assertEquals(0, atm.getCellCnt());
        atm.putCell(new BanknoteCell(new BanknoteNominal(100), 100));
        assertEquals(1, atm.getCellCnt());
    }

    @Test
    public void testPutBanknotesSuccess() {
        final BanknoteNominal[] banknotes = { new BanknoteNominal(50), new BanknoteNominal(100) };
        final var atm = new Atm();
        atm.putCell(new BanknoteCell(new BanknoteNominal(50), 10));
        atm.putCell(new BanknoteCell(new BanknoteNominal(100), 10));
        assertDoesNotThrow(() -> atm.put(banknotes));
    }

    @Test
    public void testPutBanknotesFailed() {
        final BanknoteNominal[] banknotes = { new BanknoteNominal(50), new BanknoteNominal(100) };
        final var atm = new Atm();
        atm.putCell(new BanknoteCell(new BanknoteNominal(50), 10));
        atm.putCell(new BanknoteCell(new BanknoteNominal(200), 10));
        assertThrows(RuntimeException.class, () -> atm.put(banknotes));
    }

    private Atm prepareAtm() {
        final int cellCapacity = 2;
        final var cost50 = Stream.generate(() -> new BanknoteNominal(50)).limit(cellCapacity);
        final var cost100 = Stream.generate(() -> new BanknoteNominal(100)).limit(cellCapacity);
        final var cost200 = Stream.generate(() -> new BanknoteNominal(200)).limit(cellCapacity);
        Stream<BanknoteNominal> allCost = Stream.concat(Stream.concat(cost50, cost100), cost200);
        BanknoteNominal[] inAtm = allCost.toArray(BanknoteNominal[]::new);

        final var atm = new Atm();
        atm.putCell(new BanknoteCell(new BanknoteNominal(50), cellCapacity));
        atm.putCell(new BanknoteCell(new BanknoteNominal(100), cellCapacity));
        atm.putCell(new BanknoteCell(new BanknoteNominal(200), cellCapacity));
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
        final BanknoteNominal[] expected = { new BanknoteNominal(100) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetMinimalCntBanknotesCase2() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(150);
        final BanknoteNominal[] expected = { new BanknoteNominal(100), new BanknoteNominal(50) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetMinimalCntBanknotesCase3() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(550);
        final BanknoteNominal[] expected = { new BanknoteNominal(200), new BanknoteNominal(200),
                new BanknoteNominal(100), new BanknoteNominal(50) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetAllBanknotes() {
        final var atm = prepareAtm();
        final var banknotes = atm.get(700);
        final BanknoteNominal[] expected = { new BanknoteNominal(200), new BanknoteNominal(200),
                new BanknoteNominal(100), new BanknoteNominal(100), new BanknoteNominal(50), new BanknoteNominal(50) };
        assertArrayEquals(expected, banknotes);
    }

    @Test
    public void testGetInitBalance() {
        assertEquals(0, new Atm().getBalance());
    }

    @Test
    public void testGetBalance() {
        final var atm = prepareAtm();
        assertEquals(200 * 2 + 100 * 2 + 50 * 2, atm.getBalance());
    }

    @Test
    public void testSaveCurrentState() {
        assertTrue(new Atm().saveCurrentState());
    }

    @Test
    public void testRestoreWithoutSavedState() {
        final var atm = prepareAtm();
        assertThrows(AtmException.class, () -> atm.restoreLastState());
    }

    @Test
    public void testRestoresLastState() {
        final var atm = prepareAtm();
        final var initBalance = atm.getBalance();
        atm.saveCurrentState();
        atm.get(200);
        assertTrue(atm.restoreLastState());
        assertEquals(initBalance, atm.getBalance());
    }

}
