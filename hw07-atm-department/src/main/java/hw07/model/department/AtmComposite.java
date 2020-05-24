package hw07.model.department;

import java.util.List;

import hw07.model.protocol.BalanceOperation;
import hw07.model.protocol.Operation;
import hw07.model.protocol.StateOperation;

public class AtmComposite implements BalanceOperation, StateOperation {
    private final List<? extends Operation> targets;

    public AtmComposite(List<? extends BalanceOperation> atmPark) {
        this.targets = atmPark;
    }

    @Override
    public long getBalance() {
        long result = 0;
        for (final var target : targets) {
            final var preparedTarget = (BalanceOperation)target;
            result += preparedTarget.getBalance();
        }
        return result;
    }

	@Override
	public boolean saveCurrentState() {
        for (final var target : targets) {
            final var preparedTarget = (StateOperation)target;
            preparedTarget.saveCurrentState();
        }
		return false;
	}

	@Override
	public boolean restoreLastState() {
        for (final var target : targets) {
            final var preparedTarget = (StateOperation)target;
            preparedTarget.restoreLastState();
        }
		return false;
	}
}
