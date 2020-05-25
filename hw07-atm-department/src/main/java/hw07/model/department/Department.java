package hw07.model.department;

import hw07.model.atm.Atm;
import hw07.model.department.command.GetBalanceCommand;
import hw07.model.department.command.RestoreLastStateCommand;
import hw07.model.department.command.SaveCurrentStateCommand;

public class Department implements AtmHandler {
    private final AtmObserver atmObserver = new AtmObserver();

    @Override
    public boolean addAtm(Atm newAtm) {
        return atmObserver.subscribe(newAtm);
    }

    public long getBalance() {
        final var getBalanceCommand = new GetBalanceCommand(atmObserver);
        getBalanceCommand.execute();
        return getBalanceCommand.getBalance();
    }

    public void saveAtmState() {
        new SaveCurrentStateCommand(atmObserver).execute();
    }

    public void restoreAtmState() {
        new RestoreLastStateCommand(atmObserver).execute();
    }
}
