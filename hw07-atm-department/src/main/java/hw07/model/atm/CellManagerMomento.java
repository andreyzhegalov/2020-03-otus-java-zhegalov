package hw07.model.atm;

import java.util.ArrayList;
import java.util.List;

public class CellManagerMomento {
    private List<BanknoteCell> cells = new ArrayList<>();
    private CellManager cellManager;

    public CellManagerMomento(CellManager cellManager, List<? extends BanknoteCell> cells) {
        for (final var banknoteCell : cells) {
            this.cells.add((BanknoteCell) banknoteCell.clone());
        }
        this.cellManager = cellManager;
    }

    public void restore() {
        cellManager.reset();
        for (final var banknoteCell : cells) {
            cellManager.addCell(banknoteCell);
        }
    }
}
