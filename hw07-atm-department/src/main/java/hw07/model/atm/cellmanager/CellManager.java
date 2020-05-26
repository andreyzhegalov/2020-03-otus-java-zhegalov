package hw07.model.atm.cellmanager;

import hw07.model.atm.AtmException;
import hw07.model.atm.BanknoteNominal;
import hw07.model.atm.UserAction;
import hw07.model.atm.cell.BanknoteCell;
import java.util.ArrayList;
import java.util.List;

import hw07.model.atm.cellstrategy.CellStategy;

public class CellManager implements UserAction {
    private final List<BanknoteCell> cells = new ArrayList<>();
    private final CellStategy cellStrategy;

    public CellManager(CellStategy cellStrategy) {
        this.cellStrategy = cellStrategy;
    }

    public boolean addCell(BanknoteCell newCell) {
        final boolean result = cells.add(newCell);
        cells.sort((var m1, var m2) -> (m2.getBanknoteNominal().getCost() - m1.getBanknoteNominal().getCost()));
        return result;
    }

    public long getBallance() {
        long result = 0;
        for (BanknoteCell banknoteCell : cells) {
            result += banknoteCell.getBalance();
        }
        return result;
    }

    public BanknoteCell getCell(int index) {
        if (index < 0) {
            throw new AtmException("No cell with negative index ");
        }
        if (index >= cells.size()) {
            throw new AtmException("No cell with index " + index);
        }
        return cells.get(index);
    }

    public int getCellCnt() {
        return cells.size();
    }

    @Override
    public void put(Object[] banknoteNominals) {
        final var cntByCell = cellStrategy.tryPutToCells(cells, (BanknoteNominal[]) banknoteNominals);
        putToCells(cntByCell);
    }

    @Override
    public Object[] get(long sum) {
        final var cntByCell = cellStrategy.tryGetFromCells(cells, sum);
        return getFromCells(cntByCell);
    }

    public void putToCells(int[] cnt) {
        if (cnt.length != cells.size()) {
            throw new AtmException("Can't put banknote. Error cells size");
        }
        for (int i = 0; i < cnt.length; i++) {
            final var curCell = cells.get(i);
            final int ammountAdded = cnt[i];
            curCell.put(ammountAdded);
        }
    }

    public BanknoteNominal[] getFromCells(int[] cnt) {
        if (cnt.length != cells.size()) {
            throw new AtmException("Can't get banknote. Error cells size");
        }
        final List<BanknoteNominal> result = new ArrayList<>();
        for (int i = 0; i < cnt.length; i++) {
            final var curCell = cells.get(i);
            final int cntFromCell = cnt[i];
            final var banknotesFromCell = curCell.get(cntFromCell);
            result.addAll(banknotesFromCell);
        }
        final BanknoteNominal[] resArray = new BanknoteNominal[result.size()];
        return result.toArray(resArray);
    }

    public void reset() {
        cells.clear();
    }

    public CellManagerMomento createSnapshot() {
        return new CellManagerMomento(this, cells);
    }
}
