package hw06.atm;

import java.util.ArrayList;
import java.util.List;

public class CellManager {
    private List<BanknoteCell> cells = null;

    void setCells(List<BanknoteCell> cells) {
        this.cells = cells;
    }

    public int[] tryPutToCells(Banknote[] banknotes) {
        if (cells == null) {
            throw new RuntimeException("Can't put banknote");
        }
        int[] result = new int[cells.size()];
        for (final var banknote : banknotes) {
            boolean isPut = false;
            for (int ind = 0; ind < cells.size(); ind++) {
                final var cell = cells.get(ind);
                if (!banknote.equals(cell.getBanknoteType())) {
                    continue;
                }
                int newCntInCell = result[ind] + 1;
                if (!cell.tryPut(newCntInCell)) {
                    continue;
                }
                result[ind] = newCntInCell;
                isPut = true;
                break;
            }
            if (!isPut) {
                throw new RuntimeException("Can't put banknote");
            }
        }
        return result;
    }

    public void putToCells(int[] cnt) {
        if (cnt.length != cells.size()) {
            throw new RuntimeException("Can't put banknote. Error cells size");
        }
        for (int i = 0; i < cnt.length; i++) {
            cells.get(i).put(cnt[i]);
        }
    }

    public int[] tryGetFromCells(int sum) {
        if (cells.isEmpty()) {
            throw new RuntimeException("Can't get banknote");
        }
        int curSum = sum;
        int[] result = new int[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            final var curCell = cells.get(i);
            final int canSavedCnt = curCell.tryGetSum(curSum);
            result[i] = canSavedCnt;
            curSum -= canSavedCnt * curCell.getBanknoteType().getCost();
            if (curSum == 0) {
                break;
            }
        }
        if (curSum > 0) {
            throw new RuntimeException("Can't get current sum");
        }
        return result;
    }

    public Banknote[] getFromCells(int[] cnt) {
        if (cnt.length != cells.size()) {
            throw new RuntimeException("Can't get banknote. Error cells size");
        }
        final List<Banknote> result = new ArrayList<>();
        for (int i = 0; i < cnt.length; i++) {
            final var curCell = cells.get(i);
            final int cntFromCell = cnt[i];
            result.addAll(getFromCell(curCell, cntFromCell));
        }
        final Banknote[] resArray = new Banknote[result.size()];
        return result.toArray(resArray);
    }

    private List<Banknote> getFromCell(BanknoteCell cell, int cnt) {
        final List<Banknote> result = new ArrayList<>();
        final int banknoteCost = cell.getBanknoteType().getCost();
        cell.get(cnt);
        for (int i = 0; i < cnt; i++) {
            result.add(new Banknote(banknoteCost));
        }
        return result;
    }
}
