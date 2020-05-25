package hw07.model.atm;

import hw07.model.atm.cellstrategy.CellStrategy1;
import hw07.model.protocol.BalanceOperation;
import hw07.model.protocol.StateOperation;

public class Atm implements UserAction, StaffAction, BalanceOperation, StateOperation {
    private final CellManager cellManger = new CellManager(new CellStrategy1());
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

    @Override
    public boolean saveCurrentState() {
        cellManagerState = cellManger.createSnapshot();
        return true;
    }

    @Override
    public boolean restoreLastState() {
        if(cellManagerState == null){
            throw new AtmException("No saved state");
        }
        cellManagerState.restore();
        return true;
    }
}
