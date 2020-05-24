package hw07.model.department;

import java.util.ArrayList;
import java.util.List;

import hw07.model.atm.Atm;
import hw07.model.department.command.GetBalanceCommand;
import hw07.model.department.command.RestoreLastStateCommand;
import hw07.model.department.command.SaveCurrentStateCommand;

public class Department implements AtmHandler {
    private final List<Atm> atmPark = new ArrayList<>();

    @Override
    public boolean addAtm(Atm newAtm) {
        return atmPark.add(newAtm);
    }

    public long getBalance() {
        final var getBalanceCommand = new GetBalanceCommand(atmPark);
        getBalanceCommand.execute();
        return getBalanceCommand.getBalance();
    }

    public void saveAtmState() {
        new SaveCurrentStateCommand(atmPark).execute();
    }

    public void restoreAtmState() {
        new RestoreLastStateCommand(atmPark).execute();
    }
}
