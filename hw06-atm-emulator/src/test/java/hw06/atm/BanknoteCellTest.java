package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BanknoteCellTest {
    @Test
    public void testGetTotal() {
        assertEquals(0, new BanknoteCell(new Banknote(100), 10).getOccupiedSpace());
    }

    @Test
    public void testGetTotalCnt() {
        final var cell = new BanknoteCell(new Banknote(100), 200);
        assertEquals(0, cell.getOccupiedSpace());
        cell.put(2);
        assertEquals(2, cell.getOccupiedSpace());
    }

    @Test
    public void testIsFull() {
        final var cell = new BanknoteCell(new Banknote(100), 1);
        assertFalse(cell.isFull());
        cell.put(1);
        assertTrue(cell.isFull());
    }

    @Test
    public void testIsEmpty() {
        final var cell = new BanknoteCell(new Banknote(100), 1);
        assertTrue(cell.isEmpty());
        cell.put(1);
        assertFalse(cell.isEmpty());
    }

    @Test
    public void testFreeSpace() {
        final var cell = new BanknoteCell(new Banknote(100), 2);
        assertEquals(2, cell.getFreeSpace());
        cell.put(1);
        assertEquals(1, cell.getFreeSpace());
    }

    @Test
    public void testGetMoneyType() {
        final var cell = new BanknoteCell(new Banknote(100), 200);
        assertEquals(new Banknote(100), cell.getBanknoteType());
    }

    @Test
    public void testPutForEmptyCell() {
        final var cell = new BanknoteCell(new Banknote(50), 10);
        cell.put(2);
        assertEquals(2, cell.getOccupiedSpace());
    }

    @Test
    public void testPutForNoEmptyCell() {
        final var cell = new BanknoteCell(new Banknote(50), 10);
        cell.put(2);
        cell.put(3);
        assertEquals(5, cell.getOccupiedSpace());
    }

    @Test
    public void testGetFromEmptyCell() {
        final var cell = new BanknoteCell(new Banknote(100), 10);
        assertThrows(RuntimeException.class, () -> cell.get(1));
    }

    @Test
    public void testGet() {
        final var cell = new BanknoteCell(new Banknote(50), 100);
        cell.put(2);
        cell.get(1);
        assertEquals(1, cell.getOccupiedSpace());
    }

    @Test
    public void testTryGetSumForLessCostMoney() {
        final var cell = new BanknoteCell(new Banknote(100), 10);
        assertEquals(0, cell.tryGetSum(50));
    }

    @Test
    public void testTryGetSumSuccess(){
        final var cell = new BanknoteCell(new Banknote(100), 10);
        cell.put(10);
        assertEquals(2, cell.tryGetSum(200));
    }

    @Test
    public void testTryGetAboveSum(){
        final var cell = new BanknoteCell(new Banknote(100), 10);
        cell.put(10);
        assertEquals(10, cell.tryGetSum(11*100));
    }

    @Test
    public void testTryPut(){
        final var cell = new BanknoteCell(new Banknote(100), 10);
        assertTrue(cell.tryPut(1));
    }

    @Test
    public void testTryPutInFullCell(){
        final int cellCapacity = 10;
        final var cell = new BanknoteCell(new Banknote(100), cellCapacity);
        cell.put(cellCapacity);
        assertFalse(cell.tryPut(1));
    }

    @Test
    public void testTryPutForLowFreeSpace() {
        final int cellCapacity = 10;
        final var cell = new BanknoteCell(new Banknote(100), cellCapacity);
        cell.put(cellCapacity - 1);
        assertTrue(cell.tryPut(1));
        assertFalse(cell.tryPut(2));
    }
}
