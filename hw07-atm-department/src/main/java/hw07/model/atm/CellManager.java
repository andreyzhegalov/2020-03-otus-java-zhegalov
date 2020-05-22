package hw07.model.atm;

import java.util.ArrayList;
import java.util.List;

public class CellManager {
    private List<BanknoteCell> cells = null;

    void setCells(List<BanknoteCell> cells) {
        this.cells = cells;
    }

    public int[] tryPutToCells(BanknoteNominal[] banknotes) {
        if (cells == null) {
            throw new AtmException("Can't put banknote");
        }
        final int[] result = new int[cells.size()];
        for (final var banknote : banknotes) {
            boolean isPut = false;
            for (int ind = 0; ind < cells.size(); ind++) {
                final var cell = cells.get(ind);
                if (!banknote.equals(cell.getBanknoteNominal())) {
                    continue;
                }
                final int newCntInCell = result[ind] + 1;
                if (!cell.tryPut(newCntInCell)) {
                    continue;
                }
                result[ind] = newCntInCell;
                isPut = true;
                break;
            }
            if (!isPut) {
                throw new AtmException("Can't put banknote");
            }
        }
        return result;
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

    public int[] tryGetFromCells(int sum) {
        if (cells == null || cells.isEmpty()) {
            throw new AtmException("Can't get banknote");
        }
        int curSum = sum;
        final int[] result = new int[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            final var curCell = cells.get(i);
            final int canGetFromCellSum = curCell.tryGetSum(curSum);
            final int canGetFromCellCnt = canGetFromCellSum / curCell.getBanknoteNominal().getCost();
            result[i] = canGetFromCellCnt;
            curSum -= canGetFromCellSum;
            if (curSum == 0) {
                break;
            }
        }
        if (curSum > 0) {
            throw new AtmException("Can't get current sum");
        }
        return result;
    }

    public BanknoteNominal[] getFromCells(int[] cnt) {
        if (cnt.length != cells.size()) {
            throw new AtmException("Can't get banknote. Error cells size");
        }
        final List<BanknoteNominal> result = new ArrayList<>();
        for (int i = 0; i < cnt.length; i++) {
            final var curCell = cells.get(i);
            final int cntFromCell = cnt[i];
            final var banknotesFromCell = getFromCell(curCell, cntFromCell);
            result.addAll(banknotesFromCell);
        }
        final BanknoteNominal[] resArray = new BanknoteNominal[result.size()];
        return result.toArray(resArray);
    }

    private List<BanknoteNominal> getFromCell(BanknoteCell cell, int cnt) {
        final List<BanknoteNominal> result = new ArrayList<>();
        final int banknoteCost = cell.getBanknoteNominal().getCost();
        cell.get(cnt);
        for (int i = 0; i < cnt; i++) {
            try {
                result.add(new BanknoteNominal(banknoteCost));
            } catch (Exception e) {
                throw new AtmException("Internal error. Create banknote with nominal:" +  banknoteCost );
            }
        }
        return result;
    }
}
