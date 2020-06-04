package hw07.model.atm;

import hw07.model.atm.cell.BanknoteCell;

public interface CellAction {
    boolean putCell(BanknoteCell cell);
}
