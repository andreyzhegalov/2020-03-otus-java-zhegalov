package hw07.model.department;

import java.util.List;

import hw07.model.protocol.BalanceCommand;

public class GroupAtm implements BalanceCommand {
    private final List<? extends BalanceCommand> atmPark;

    public GroupAtm(List<? extends BalanceCommand> atmPark) {
        this.atmPark = atmPark;
    }

    @Override
    public long getBalance() {
        long result = 0;
        for (final var atm : atmPark) {
            result += atm.getBalance();
        }
        return result;
    }
}
