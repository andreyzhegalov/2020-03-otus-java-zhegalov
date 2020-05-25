package hw07.model.department.command;

import hw07.model.department.AtmManager;

public class GetBalanceCommand extends Command {
    private long balance;

    public GetBalanceCommand(AtmManager atmManager) {
        super(atmManager);
    }

    @Override
    public boolean execute() {
        balance = atmManager.sendGetBalance();
        return true;
    }

    public long getBalance() {
        return balance;
    }
}
