package hw07.model.department;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import hw07.model.atm.Atm;

public class DepartmentTest {
    @Test
    public void testAddAtm() {
        final var atm = new Atm();
        assertTrue(new Department().addAtm(atm));
    }

    @Test
    public void testGetBalance() {
        Atm mockedAtm1 = Mockito.mock(Atm.class);
        Mockito.when(mockedAtm1.getBalance()).thenReturn(100L);
        Atm mockedAtm2 = Mockito.mock(Atm.class);
        Mockito.when(mockedAtm2.getBalance()).thenReturn(50L);

        final var department = new Department();
        department.addAtm(mockedAtm1);
        department.addAtm(mockedAtm2);

        assertEquals(150, department.getBalance());

        Mockito.verify(mockedAtm1, Mockito.times(1)).getBalance();
        Mockito.verify(mockedAtm2, Mockito.times(1)).getBalance();
    }

    @Test
    public void testSaveAtmState(){
        Atm mockedAtm1 = Mockito.mock(Atm.class);
        Mockito.when(mockedAtm1.getBalance()).thenReturn(100L);
        Atm mockedAtm2 = Mockito.mock(Atm.class);
        Mockito.when(mockedAtm2.getBalance()).thenReturn(50L);

        final var department = new Department();
        department.addAtm(mockedAtm1);
        department.addAtm(mockedAtm2);

        department.saveAtmState();

        Mockito.verify(mockedAtm1, Mockito.times(1)).saveState();
        Mockito.verify(mockedAtm2, Mockito.times(1)).saveState();
    }

    @Test
    public void testRestoreState(){
        Atm mockedAtm1 = Mockito.mock(Atm.class);
        Mockito.when(mockedAtm1.getBalance()).thenReturn(100L);
        Atm mockedAtm2 = Mockito.mock(Atm.class);
        Mockito.when(mockedAtm2.getBalance()).thenReturn(50L);

        final var department = new Department();
        department.addAtm(mockedAtm1);
        department.addAtm(mockedAtm2);

        department.saveAtmState();
        department.restoreAtmState();

        Mockito.verify(mockedAtm1, Mockito.times(1)).restoreState();
        Mockito.verify(mockedAtm2, Mockito.times(1)).restoreState();
    }
}
