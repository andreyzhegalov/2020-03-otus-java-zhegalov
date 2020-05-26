package hw07.model.department.internal;

import hw07.model.protocol.Protocol;

public class AtmChainHandler implements ChainHandler {
    private ChainHandler next = null;
    private Protocol target = null;

    public AtmChainHandler(Protocol target) {
        this.target = target;
    }

    @Override
    public void setNext(ChainHandler nextHandler) {
        next = nextHandler;
    }

    @Override
    public long summarizeBallance(long initBalance) {
        final var balance = initBalance + target.getBalance();
        if (next != null) {
            return next.summarizeBallance(balance);
        }
        return balance;
    }

    @Override
    public boolean sendSaveState() {
        target.saveCurrentState();
        if (next != null) {
            next.sendSaveState();
        }
        return true;
    }

    @Override
    public boolean sendRestoreState() {
        target.restoreLastState();
        if (next != null) {
            next.sendRestoreState();
        }
        return true;
    }
}
