package hw07.model.atm.cellmanager;

import hw07.model.atm.cell.BanknoteCell;
import java.util.ArrayList;
import java.util.List;

public class CellManagerMomento {
    private List<BanknoteCell> cells = new ArrayList<>();
    private CellManager cellManager;

    public CellManagerMomento(CellManager cellManager, List<BanknoteCell> cells) {
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
