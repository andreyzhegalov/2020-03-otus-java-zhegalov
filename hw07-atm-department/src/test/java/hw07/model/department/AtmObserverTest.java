package hw07.model.department;

import static org.junit.jupiter.api.Assertions.assertThrows;

import hw07.model.department.internal.AtmObserver;
import org.junit.jupiter.api.Test;

public class AtmObserverTest {
    @Test
    public void testGetBalanceWhithoutListeners() {
        assertThrows(DepartmentException.class, () -> new AtmObserver().sendGetBalance());
    }

    @Test
    public void testSaveCurrentStateWhithoutListeners(){
        assertThrows(DepartmentException.class, () -> new AtmObserver().sendSaveCurrentState());
    }

    @Test
    public void testRestoreCurrentStateWhithoutListeners(){
        assertThrows(DepartmentException.class, () -> new AtmObserver().sendRestoreLastState());
    }
}
