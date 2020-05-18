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
    private List<BanknoteCell> cells;

    @BeforeEach
    void init() {
        cellManager = new CellManager();

        cells = Arrays.asList(new BanknoteCell(new Banknote(100), 10), new BanknoteCell(new Banknote(50), 10));
        cellManager.setCells(cells);
    }

    @Test
    public void testTryPutToCellWithoutCells() {
        final var cellManager = new CellManager();

        final Banknote[] banknote = { new Banknote(50) };
        assertThrows(RuntimeException.class, () -> cellManager.tryPutToCells(banknote));
    }

    @Test
    public void testTryPutInOneCell() {
        final Banknote[] banlnotes = { new Banknote(50) };
        final var result = cellManager.tryPutToCells(banlnotes);
        final int[] expect = { 0, 1 };
        assertArrayEquals(expect, result);
    }

    @Test
    public void testTryPutInTwoCell() {
        final Banknote[] banknotes = { new Banknote(100), new Banknote(50) };
        final var result = cellManager.tryPutToCells(banknotes);
        final int[] expect = { 1, 1 };
        assertArrayEquals(expect, result);
    }

    @Test
    public void testTryPutToCellError() {
        final Banknote[] banknotes = { new Banknote(200) };
        assertThrows(RuntimeException.class, () -> cellManager.tryPutToCells(banknotes));
    }

    @Test
    public void testPutInErrorCntCells() {
        final int[] cntByCell = { 0, 1, 0 };
        assertThrows(RuntimeException.class, () -> cellManager.putToCells(cntByCell));
    }

    @Test
    public void testPutToNotOneCell() {
        final int[] cntByCell = { 0, 0 };
        cellManager.putToCells(cntByCell);
        assertEquals(10, cells.get(0).getFreeSpace());
        assertEquals(10, cells.get(1).getFreeSpace());
    }

    @Test
    public void testPutInOneCell() {
        final int[] cntByCell = { 0, 1 };
        cellManager.putToCells(cntByCell);
        assertEquals(10, cells.get(0).getFreeSpace());
        assertEquals(9, cells.get(1).getFreeSpace());
    }

    @Test
    public void testTryGetFromNotOneCell() {
        final var cellManager = new CellManager();
        assertThrows(RuntimeException.class, () -> cellManager.tryGetFromCells(100));
    }

    @Test
    public void testTryGetBanknote() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);
        final int[] expectedCnt = { 2, 1 };
        assertArrayEquals(expectedCnt, cellManager.tryGetFromCells(250));
    }

    @Test
    public void testTryGetAllBanknote() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);
        final int[] expectedCnt = { 10, 10 };
        assertArrayEquals(expectedCnt, cellManager.tryGetFromCells(100*10 + 50*10));
    }

    @Test
    public void testGetFromFirstCell() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);

        final int[] getCntFromCell = { 1, 0 };
        final var banknotes = cellManager.getFromCells(getCntFromCell);
        assertEquals(1, banknotes.length);
        assertEquals(new Banknote(100), banknotes[0]);
        assertEquals(9, cells.get(0).getOccupiedSpace());
        assertEquals(10, cells.get(1).getOccupiedSpace());
    }

    @Test
    public void testGetFromSecondCell() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);

        final int[] getCntFromCell = { 0, 1 };
        final var banknotes = cellManager.getFromCells(getCntFromCell);
        assertEquals(1, banknotes.length);
        assertEquals(new Banknote(50), banknotes[0]);
        assertEquals(10, cells.get(0).getOccupiedSpace());
        assertEquals(9, cells.get(1).getOccupiedSpace());
    }
}
