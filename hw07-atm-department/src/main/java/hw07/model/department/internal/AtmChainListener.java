package hw07.model.department.internal;

import hw07.model.protocol.Protocol;

public class AtmChainListener implements ChainListener {
    private ChainListener next = null;
    private Protocol atm = null;

    public AtmChainListener(Protocol atm) {
        this.atm = atm;
    }

    @Override
    public void setNext(ChainListener nextListener) {
        next = nextListener;
    }

    @Override
    public long summarizeBallance(long initBalance) {
        final var balance = initBalance + atm.getBalance();
        if (next != null) {
            return next.summarizeBallance(balance);
        }
        return balance;
    }

    @Override
    public boolean sendSaveState() {
        atm.saveCurrentState();
        if (next != null) {
            next.sendSaveState();
        }
        return true;
    }

    @Override
    public boolean sendRestoreState() {
        atm.restoreLastState();
        if (next != null) {
            next.sendRestoreState();
        }
        return true;
    }
}