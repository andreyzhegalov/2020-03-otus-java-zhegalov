package hw07.model.department;

import java.util.ArrayList;
import java.util.List;

import hw07.model.protocol.BalanceOperation;
import hw07.model.protocol.Protocol;
import hw07.model.protocol.StateOperation;

public class AtmManager {
    private final List<Protocol> listeners = new ArrayList<>();

    public boolean subscribe(Protocol listener) {
        return listeners.add(listener);
    }

    public long sendGetBalance() {
        long result = 0;
        for (final var target : listeners) {
            final var preparedTarget = (BalanceOperation) target;
            result += preparedTarget.getBalance();
        }
        return result;
    }

    public boolean sendSaveCurrentState() {
        for (final var target : listeners) {
            final var preparedTarget = (StateOperation) target;
            preparedTarget.saveCurrentState();
        }
        return false;
    }

    public boolean sendRestoreLastState() {
        for (final var target : listeners) {
            final var preparedTarget = (StateOperation) target;
            preparedTarget.restoreLastState();
        }
        return false;
    }

}

