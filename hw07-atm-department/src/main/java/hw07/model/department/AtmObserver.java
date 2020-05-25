package hw07.model.department;

import hw07.model.protocol.Protocol;

public class AtmObserver {
    private ChainHandler lastHandler = null;
    private ChainHandler firstHandle = null;

    public boolean subscribe(Protocol atm) {
        if (lastHandler == null) {
            lastHandler = new AtmChainHandler(atm);
            firstHandle = lastHandler;
        } else {
            final var nextHandler = new AtmChainHandler(atm);
            lastHandler.setNext(nextHandler);
            lastHandler = nextHandler;
        }
        return true;
    }

    public long sendGetBalance() {
        if (firstHandle == null) {
            throw new DepartmentException("no listeners");
        }
        return firstHandle.summarizeBallance(0);
    }

    public boolean sendSaveCurrentState() {
        if (firstHandle == null) {
            throw new DepartmentException("no listeners");
        }
        return firstHandle.sendSaveState();
    }

    public boolean sendRestoreLastState() {
        if (firstHandle == null) {
            throw new DepartmentException("no listeners");
        }
        return firstHandle.sendRestoreState();
    }

}
