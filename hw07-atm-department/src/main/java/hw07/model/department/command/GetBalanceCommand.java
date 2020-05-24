package hw07.model.department.command;

import java.util.List;

import hw07.model.atm.Atm;
import hw07.model.department.AtmComposite;

public class GetBalanceCommand extends Command {
    private long balance;

    public GetBalanceCommand(List<Atm> atmPark) {
        super(atmPark);
    }

    @Override
    public boolean execute() {
        balance = new AtmComposite(atmPark).getBalance();
        return true;
    }

    public long getBalance() {
        return balance;
    }
}
