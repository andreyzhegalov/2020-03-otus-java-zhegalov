package hw07.model.department;

import hw07.model.atm.Atm;
import hw07.model.department.command.GetBalanceCommand;
import hw07.model.department.command.RestoreLastStateCommand;
import hw07.model.department.command.SaveCurrentStateCommand;
import hw07.model.department.internal.AtmObserver;

public class Department{
    private final AtmObserver atmObserver = new AtmObserver();

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
