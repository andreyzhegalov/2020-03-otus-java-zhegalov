package hw07.model.department.internal;

import hw07.model.protocol.Protocol;

public class AtmChainListener implements ChainListener {
    private ChainListener next = null;
    private Protocol atmProtocol = null;

    public AtmChainListener(Protocol atm) {
        this.atmProtocol = atm;
    }

    @Override
    public void setNext(ChainListener nextListener) {
        next = nextListener;
    }

    @Override
    public long summarizeBallance(long initBalance) {
        final var balance = initBalance + atmProtocol.getBalance();
        if (next != null) {
            return next.summarizeBallance(balance);
        }
        return balance;
    }

    @Override
    public boolean sendSaveState() {
        atmProtocol.saveCurrentState();
        if (next != null) {
            next.sendSaveState();
        }
        return true;
    }

    @Override
    public boolean sendRestoreState() {
        atmProtocol.restoreLastState();
        if (next != null) {
            next.sendRestoreState();
        }
        return true;
    }
}
