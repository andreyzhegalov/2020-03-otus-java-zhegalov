package hw07.model.atm;

import hw07.model.atm.cell.BanknoteCell;
import hw07.model.atm.cellmanager.CellManager;
import hw07.model.atm.cellmanager.CellManagerMomento;
import hw07.model.atm.cellstrategy.CellStrategy1;

public class Atm implements UserAction, CellAction {
    private final CellManager cellManger = new CellManager(new CellStrategy1());
    private CellManagerMomento cellManagerState = null;

    public int getCellCnt() {
        return cellManger.getCellCnt();
    }

    @Override
    public void put(Object[] banknotes) {
        cellManger.put(banknotes);
    }

    @Override
    public Object[] get(long sum) {
        return cellManger.get(sum);
    }

    @Override
    public boolean putCell(BanknoteCell newCell) {
        return cellManger.addCell(newCell);
    }

    public long getBalance() {
        return cellManger.getBallance();
    }

    public boolean saveCurrentState() {
        cellManagerState = cellManger.createSnapshot();
        return true;
    }

    public boolean restoreLastState() {
        if (cellManagerState == null) {
            throw new AtmException("No saved state");
        }
        cellManagerState.restore();
        return true;
    }
}
