package hw07.model.atm;

import hw07.model.department.Subscriber;
import hw07.model.protocol.BalanceCommand;
import hw07.model.protocol.StateCommand;

public class Atm implements UserAction, StaffAction, BalanceCommand, StateCommand, Subscriber {
    private final CellManager cellManger = new CellManager();
    private CellManagerMomento cellManagerState = null;

    @Override
    public long getBalance() {
        return cellManger.getBallance();
    }

    public int getCellCnt() {
        return cellManger.getCellCnt();
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
    public boolean putCell(BanknoteCell newCell) {
        return cellManger.addCell(newCell);
    }

    @Override
    public boolean saveCurrentState() {
        // TODO is this needed?
        cellManagerState = cellManger.createSnapshot();
        return true;
    }

    @Override
    public boolean restoreLastState() {
        // TODO is this needed?
        if(cellManagerState == null){
            throw new AtmException("No saved state");
        }
        cellManagerState.restore();
        return true;
    }

	@Override
	public void saveState() {
        cellManagerState = cellManger.createSnapshot();
	}

	@Override
	public void restoreState() {
        if(cellManagerState == null){
            throw new AtmException("No saved state");
        }
        cellManagerState.restore();
	}
}
