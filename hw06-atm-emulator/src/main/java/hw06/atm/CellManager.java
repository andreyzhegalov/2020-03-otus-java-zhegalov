package hw06.atm;

import java.util.List;

public class CellManager {
    private List<MoneyCell> cells = null;

    void setCells(List<MoneyCell> cells) {
        this.cells = cells;
    }

    int[] tryPut(Money[] moneys) {
        if (cells == null) {
            throw new RuntimeException("Can't put money");
        }
        int[] result = new int[cells.size()];
        for (final var money : moneys) {
            boolean moneyPuted = false;
            for (int ind = 0; ind < cells.size(); ind++) {
                final var cell = cells.get(ind);
                if (cell.isFull()) {
                    continue;
                }
                if (money.equals(cell.getMoneyType())) {
                    int newCellCnt = result[ind] + 1;
                    if (tryPutToCell(cell, newCellCnt)) {
                        result[ind] = newCellCnt;
                        moneyPuted = true;
                        break;
                    }
                }
            }
            if (!moneyPuted) {
                throw new RuntimeException("Can't put money");
            }
        }
        return result;
    }

    private boolean tryPutToCell(MoneyCell cell, int cnt) {
        return !cell.isFull() && cell.freeSpace() >= cnt;
    }

    public void putMoney(int[] cnt) {
        if (cnt.length != cells.size()) {
            throw new RuntimeException("Can't put money. Error cells size");
        }
        for (int i = 0; i < cnt.length; i++) {
            cells.get(i).put(cnt[i]);
        }
    }
}
