package hw07.model.atm.cellstrategy;

import java.util.List;

import hw07.model.atm.BanknoteCell;
import hw07.model.atm.BanknoteNominal;

public interface CellStategy {

    public int[] tryGetFromCells(List<BanknoteCell> cells, long sum);

    public int[] tryPutToCells(List<BanknoteCell> cells, BanknoteNominal[] banknotes);
}

