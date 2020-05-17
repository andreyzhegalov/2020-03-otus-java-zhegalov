package hw06.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MoneyCellTest {
    @Test
    public void testGetTotal() {
        assertEquals(0, new MoneyCell(new Money(100), 10).getTotalCnt());
    }

    @Test
    public void testGetTotalCnt() {
        final var cell = new MoneyCell(new Money(100), 200);
        assertEquals(0, cell.getTotalCnt());
        cell.put(2);
        assertEquals(2, cell.getTotalCnt());
    }

    @Test
    public void testIsFull(){
        final var cell = new MoneyCell(new Money(100), 1);
        assertFalse( cell.isFull());
        cell.put(1);
        assertTrue(cell.isFull());
    }

    @Test
    public void testFreeSpace(){
        final var cell = new MoneyCell(new Money(100), 2);
        assertEquals(2, cell.freeSpace());
        cell.put(1);
        assertEquals(1, cell.freeSpace());
    }

    @Test
    public void testGetMoneyType() {
        final var cell = new MoneyCell(new Money(100), 200);
        assertEquals(new Money(100), cell.getMoneyType());
    }

    @Test
    public void testPutForEmptyCell() {
        final var cell = new MoneyCell(new Money(50), 10);
        cell.put(2);
        assertEquals(2, cell.getTotalCnt());
    }

    @Test
    public void testPutForNoEmptyCell() {
        final var cell = new MoneyCell(new Money(50), 10);
        cell.put(2);
        cell.put(3);
        assertEquals(5, cell.getTotalCnt());
    }

    @Test
    public void testGetFromEmptyCell() {
        final var cell = new MoneyCell(new Money(100), 10);
        assertThrows(RuntimeException.class, () -> cell.get(1));
    }

    @Test
    public void testGet() {
        final var cell = new MoneyCell(new Money(50), 100);
        cell.put(2);
        cell.get(1);
        assertEquals(1, cell.getTotalCnt());
    }

}
