package hw07.model.atm;

import java.util.ArrayList;
import java.util.List;

import hw07.model.protocol.BalanceCommand;
import hw07.model.protocol.StateCommand;

public class Atm implements UserAction, StaffAction, BalanceCommand, StateCommand {
    private final List<BanknoteCell> cells = new ArrayList<>();
    private final CellManager cellManger = new CellManager();

    @Override
    public long getBalance() {
        long result = 0;
        for (BanknoteCell banknoteCell : cells) {
            result += banknoteCell.getBalance();
        }
        return result;
    }

    public int getCellCnt() {
        return cells.size();
    }

    @Override
    public void put(Object[] banknotes) {
        final var cntByCell = cellManger.tryPutToCells((BanknoteNominal[]) banknotes);
        cellManger.putToCells(cntByCell);
    }

    @Override
    public Object[] get(int sum) {
        final var cntByCell = cellManger.tryGetFromCells(sum);
        return cellManger.getFromCells(cntByCell);
    }

    @Override
    public void putCell(BanknoteCell cell) {
        cells.add(cell);
        cells.sort((var m1, var m2) -> (m2.getBanknoteNominal().getCost() - m1.getBanknoteNominal().getCost()));
        cellManger.setCells(cells);
    }

    @Override
    public boolean saveCurrentState() {
        return true;
    }

	@Override
	public boolean restoreLastState() {
		return true;
	}
}
