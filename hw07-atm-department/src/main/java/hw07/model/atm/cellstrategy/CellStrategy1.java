package hw07.model.atm.cellstrategy;

import java.util.List;

import hw07.model.atm.cell.BanknoteCell;
import hw07.model.atm.BanknoteNominal;

public class CellStrategy1 implements CellStategy {

    @Override
    public int[] tryGetFromCells(List<BanknoteCell> cells, long sum) {
        if (cells == null || cells.isEmpty()) {
            throw new CellStrategyException("Can't get banknote");
        }
        var curSum = sum;
        final int[] result = new int[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            final var curCell = cells.get(i);
            final long canGetFromCellSum = curCell.tryGetSum(curSum);
            final int canGetFromCellCnt = (int) (canGetFromCellSum / curCell.getBanknoteNominal().getCost());
            result[i] = canGetFromCellCnt;
            curSum -= canGetFromCellSum;
            if (curSum == 0) {
                break;
            }
        }
        if (curSum > 0) {
            throw new CellStrategyException("Can't get current sum");
        }
        return result;
    }

    @Override
    public int[] tryPutToCells(List<BanknoteCell> cells, BanknoteNominal[] banknotes) {
        if (cells == null) {
            throw new CellStrategyException("Can't put banknote");
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
                throw new CellStrategyException("Can't put banknote");
            }
        }
        return result;
    }
}
