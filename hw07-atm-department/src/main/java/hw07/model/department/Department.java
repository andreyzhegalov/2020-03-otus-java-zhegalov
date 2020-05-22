package hw07.model.department;

import java.util.ArrayList;
import java.util.List;

import hw07.model.atm.Atm;

public class Department implements DepartmentAction, AtmHandler {
    private final List<Atm> atmPark = new ArrayList<>();

    @Override
    public long getBalance() {
        return new GroupAtm(atmPark).getBalance();
    }

    @Override
    public boolean addAtm(Atm newAtm) {
        return atmPark.add(newAtm);
    }
}
