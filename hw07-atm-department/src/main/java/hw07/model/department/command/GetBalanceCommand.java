package hw07.model.department.command;

import hw07.model.department.internal.ChainListener;

public class GetBalanceCommand extends Command {
    private long balance;

    public long getBalance() {
        return balance;
    }

    @Override
    public void execute(ChainListener listener) {
        balance = listener.summarizeBallance(balance);
    }
}
