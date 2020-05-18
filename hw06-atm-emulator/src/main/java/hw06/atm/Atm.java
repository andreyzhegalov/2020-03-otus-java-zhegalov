package hw06.atm;

import java.util.ArrayList;
import java.util.List;

public class Atm implements UserAction, StaffAction {
    private final List<BanknoteCell> cells = new ArrayList<>();
    private final CellManager cellManger = new CellManager();

    public int getBalance() {
        return 0;
    }

    public int getCellCnt() {
        return cells.size();
    }

    @Override
    public void put(Object[] banknotes) {
        final var cntByCell = cellManger.tryPutToCells((Banknote[]) banknotes);
        cellManger.putToCells(cntByCell);
    }

    @Override
    public Object[] get(int summ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putCell(BanknoteCell cell) {
        cells.add(cell);
        cells.sort((var m1, var m2) -> (m1.getBanknoteType().getCost() - m2.getBanknoteType().getCost()));
        cellManger.setCells(cells);
    }

    @Override
    public void getCell() {
        throw new UnsupportedOperationException();
    }

}
