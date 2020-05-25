package hw07.model.atm.cellstrategy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw07.model.atm.BanknoteCell;
import hw07.model.atm.BanknoteNominal;

public class CellStratagy1Tests {
    private List<BanknoteCell> cells;
    private final static int CELL_CAPACITY = 10;

    @BeforeEach
    void init() {
        cells = new ArrayList<>();
        cells.add(new BanknoteCell(new BanknoteNominal(100), CELL_CAPACITY));
        cells.add(new BanknoteCell(new BanknoteNominal(50), CELL_CAPACITY));
    }

    @Test
    public void testTryPutWithoutCells() {
        final List<BanknoteCell> cells = new ArrayList<>();
        final BanknoteNominal[] banknotes = { new BanknoteNominal(200) };
        assertThrows(CellStrategyException.class, () -> new CellStrategy1().tryPutToCells(cells, banknotes));
    }

    @Test
    public void testTryPutInOneCell() {
        final BanknoteNominal[] banknotes = { new BanknoteNominal(50) };
        final var result = new CellStrategy1().tryPutToCells(cells, banknotes);
        final int[] expect = { 0, 1 };
        assertArrayEquals(expect, result);
    }

    @Test
    public void testTryPutInTwoCell() {
        final BanknoteNominal[] banknotes = { new BanknoteNominal(100), new BanknoteNominal(50) };
        final var result = new CellStrategy1().tryPutToCells(cells, banknotes);
        final int[] expect = { 1, 1 };
        assertArrayEquals(expect, result);
    }

    @Test
    public void testTryPutToCellError() {
        final BanknoteNominal[] banknotes = { new BanknoteNominal(200) };
        assertThrows(RuntimeException.class, () -> new CellStrategy1().tryPutToCells(cells, banknotes));
    }

    @Test
    public void testTryGetWithoutCells() {
        final List<BanknoteCell> cells = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> new CellStrategy1().tryGetFromCells(cells, 100));
    }

    private void fillCells() {
        for (BanknoteCell banknoteCell : cells) {
            banknoteCell.put(CELL_CAPACITY);
        }
    }

    @Test
    public void testTryGetBanknote() {
        fillCells();
        final int[] expectedCnt = { 2, 1 };
        assertArrayEquals(expectedCnt, new CellStrategy1().tryGetFromCells(cells, 250));
    }

    @Test
    public void testTryGetAllBanknote() {
        fillCells();
        final int[] expectedCnt = { 10, 10 };
        assertArrayEquals(expectedCnt, new CellStrategy1().tryGetFromCells(cells, 100 * 10 + 50 * 10));
    }
}
