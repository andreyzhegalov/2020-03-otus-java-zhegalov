package hw07.model.department;

import hw07.model.atm.Atm;
import hw07.model.department.command.GetBalanceCommand;
import hw07.model.department.command.RestoreLastStateCommand;
import hw07.model.department.command.SaveCurrentStateCommand;

public class Department implements AtmHandler {
    private final AtmManager atmManager = new AtmManager();

    @Override
    public boolean addAtm(Atm newAtm) {
        return atmManager.subscribe(newAtm);
    }

    public long getBalance() {
        final var getBalanceCommand = new GetBalanceCommand(atmManager);
        getBalanceCommand.execute();
        return getBalanceCommand.getBalance();
    }

    public void saveAtmState() {
        new SaveCurrentStateCommand(atmManager).execute();
    }

    public void restoreAtmState() {
        new RestoreLastStateCommand(atmManager).execute();
    }
}
