package hw07.model.atm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw07.model.atm.cellstrategy.CellStrategy1;

public class CellManagerTest {
    private CellManager cellManager;

    @BeforeEach
    void init() {
        cellManager = new CellManager( new CellStrategy1());
        cellManager.addCell(new BanknoteCell(new BanknoteNominal(100), 10));
        cellManager.addCell(new BanknoteCell(new BanknoteNominal(50), 10));
    }

    @Test
    public void testGetCnt(){
        assertEquals(2, cellManager.getCellCnt());
    }

    @Test
    public void getCellSuccesful(){
        assertDoesNotThrow(()->cellManager.getCell(1));
    }

    @Test
    public void getCellFail(){
        assertThrows( AtmException.class, ()->cellManager.getCell(2));
    }

    @Test
    public void getCellWhithNegativeIndex(){
        assertThrows(AtmException.class, ()->cellManager.getCell(-1));
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
        assertEquals(10, cellManager.getCell(0).getFreeSpace());
        assertEquals(10, cellManager.getCell(1).getFreeSpace());
    }

    @Test
    public void testPutInOneCell() {
        final int[] cntByCell = { 0, 1 };
        cellManager.putToCells(cntByCell);
        assertEquals(10, cellManager.getCell(0).getFreeSpace());
        assertEquals(9, cellManager.getCell(1).getFreeSpace());
    }

    @Test
    public void testGetFromErrorCntCell() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);
        final int[] getCntFromCell = { 1, 0, 1 };
        assertThrows(RuntimeException.class, () -> cellManager.getFromCells(getCntFromCell));
    }

    @Test
    public void testGetFromFirstCell() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);
        final int[] getCntFromCell = { 1, 0 };
        final var banknotes = cellManager.getFromCells(getCntFromCell);
        assertEquals(1, banknotes.length);
        assertEquals(new BanknoteNominal(100), banknotes[0]);
        assertEquals(9, cellManager.getCell(0).getOccupiedSpace());
        assertEquals(10, cellManager.getCell(1).getOccupiedSpace());
    }

    @Test
    public void testGetFromSecondCell() {
        final int[] initCntInCells = { 10, 10 };
        cellManager.putToCells(initCntInCells);

        final int[] getCntFromCell = { 0, 1 };
        final var banknotes = cellManager.getFromCells(getCntFromCell);
        assertEquals(1, banknotes.length);
        assertEquals(new BanknoteNominal(50), banknotes[0]);
        assertEquals(10, cellManager.getCell(0).getOccupiedSpace());
        assertEquals(9, cellManager.getCell(1).getOccupiedSpace());
    }

    @Test
    public void testReset(){
        final int[] cntByCell = { 1, 0 };
        cellManager.putToCells(cntByCell);
        cellManager.reset();
        assertEquals(0, cellManager.getBallance());
    }

    @Test
    public void testCreateSnapshort(){
        assertDoesNotThrow(()->cellManager.createSnapshot());
    }
}
