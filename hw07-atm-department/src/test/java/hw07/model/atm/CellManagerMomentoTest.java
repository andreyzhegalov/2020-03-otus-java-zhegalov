package hw07.model.atm;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CellManagerMomentoTest {
    @Test
    public void testResore(){
        CellManager mockedCellManager = Mockito.mock(CellManager.class);
        final List<BanknoteCell> cells = new ArrayList<>();
        cells.add(new BanknoteCell(new BanknoteNominal(100), 10));

        final var momento = new CellManagerMomento(mockedCellManager, cells);
        momento.restore();

        Mockito.verify(mockedCellManager, Mockito.times(1)).reset();
        Mockito.verify( mockedCellManager, Mockito.times(cells.size())).addCell(Mockito.any());
    }
}

