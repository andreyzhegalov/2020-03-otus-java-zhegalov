package hw06.atm;

import java.util.ArrayList;
import java.util.List;

public class Atm implements MoneyAction, StaffAction {
    private final List<MoneyCell> cells = new ArrayList<>();

    public int getBalance() {
        return 0;
    }

    public int getCellCnt() {
        return cells.size();
    }

    @Override
    public void put(Object[] moneys) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object[] get(int summ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putCell(MoneyCell cell) {
        cells.add(cell);
    }

    @Override
    public void getCell() {
        throw new UnsupportedOperationException();
    }

}
