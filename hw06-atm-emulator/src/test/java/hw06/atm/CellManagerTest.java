package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellManagerTest {
    private CellManager cellManager;
    private List<MoneyCell> cells;

    @BeforeEach
    void init() {
        cellManager = new CellManager();

        cells = Arrays.asList(new MoneyCell(new Money(100), 10), new MoneyCell(new Money(50), 10));
        cellManager.setCells(cells);
    }

    @Test
    public void testTryPutWithoutCells() {
        final var cellManager = new CellManager();

        final Money[] moneys = { new Money(50) };
        assertThrows(RuntimeException.class, () -> cellManager.tryPut(moneys));
    }

    @Test
    public void testTryPutInOneCell() {
        final Money[] moneys = { new Money(50) };
        final var result = cellManager.tryPut(moneys);
        final int[] expect = { 0, 1 };
        assertArrayEquals(expect, result);
    }

    @Test
    public void testTryPutInTwoCell() {
        final Money[] moneys = { new Money(100), new Money(50) };
        final var result = cellManager.tryPut(moneys);
        final int[] expect = { 1, 1 };
        assertArrayEquals(expect, result);
    }

    @Test
    public void testTryPutError() {
        final Money[] moneys = { new Money(200) };
        assertThrows(RuntimeException.class, () -> cellManager.tryPut(moneys));
    }

    @Test
    public void testPutMoneyInErrorCntCells() {
        final int[] cntByCell = { 0, 1, 0 };
        assertThrows(RuntimeException.class, () -> cellManager.putMoney(cntByCell));
    }

    @Test
    public void testPutMoneyNotAnyCell() {
        final int[] cntByCell = { 0, 0 };
        cellManager.putMoney(cntByCell);
        assertEquals(10, cells.get(0).freeSpace());
        assertEquals(10, cells.get(1).freeSpace());
    }

    @Test
    public void testPutMoneyInOneCell() {
        final int[] cntByCell = { 0, 1 };
        cellManager.putMoney(cntByCell);
        assertEquals(10, cells.get(0).freeSpace());
        assertEquals(9, cells.get(1).freeSpace());
    }
}
