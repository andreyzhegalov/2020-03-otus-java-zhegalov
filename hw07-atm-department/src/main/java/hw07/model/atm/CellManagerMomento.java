package hw07.model.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CellManagerMomento {
    private List<BanknoteCell> cells = new ArrayList<>();
    private CellManager cellManager;

    public CellManagerMomento(CellManager cellManager, List<BanknoteCell> cells) {
        // TODO add prototype copy
        this.cells = cells.stream().collect(Collectors.toList());
        this.cellManager = cellManager;
    }

    public void restore() {
        cellManager.reset();
        for (BanknoteCell banknoteCell : cells) {
            cellManager.addCell(banknoteCell);
        }
    }
}
