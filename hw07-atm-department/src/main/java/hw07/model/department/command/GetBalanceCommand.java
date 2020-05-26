package hw07.model.department.command;

import hw07.model.department.internal.AtmObserver;

public class GetBalanceCommand extends Command {
    private long balance;

    public GetBalanceCommand(AtmObserver atmObserver) {
        super(atmObserver);
    }

    @Override
    public boolean execute() {
        balance = atmObserver.sendGetBalance();
        return true;
    }

    public long getBalance() {
        return balance;
    }
}
