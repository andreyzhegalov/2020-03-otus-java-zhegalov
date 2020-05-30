package hw07.model.department.internal;

import hw07.model.department.DepartmentException;
import hw07.model.department.command.Command;
import hw07.model.protocol.Protocol;

public class AtmObserver {
    private ChainListener lastListener = null;
    private ChainListener firstListener = null;

    public boolean subscribe(Protocol listener) {
        if (lastListener == null) {
            lastListener = new AtmChainListener(listener);
            firstListener = lastListener;
        } else {
            final var nextHandler = new AtmChainListener(listener);
            lastListener.setNext(nextHandler);
            lastListener = nextHandler;
        }
        return true;
    }

    public void sendCommand(Command command) {
        if (firstListener == null) {
            throw new DepartmentException("no listeners");
        }
        command.execute(firstListener);
    }

    public long sendGetBalance() {
        if (firstListener == null) {
            throw new DepartmentException("no listeners");
        }
        return firstListener.summarizeBallance(0);
    }

    public boolean sendSaveCurrentState() {
        if (firstListener == null) {
            throw new DepartmentException("no listeners");
        }
        return firstListener.sendSaveState();
    }

    public boolean sendRestoreLastState() {
        if (firstListener == null) {
            throw new DepartmentException("no listeners");
        }
        return firstListener.sendRestoreState();
    }

}
