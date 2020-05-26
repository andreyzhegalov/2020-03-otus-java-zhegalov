package hw07.model.department.internal;

public interface ChainHandler {
    public void setNext(ChainHandler nextHandler);

    public long summarizeBallance(long initBalance);

    public boolean sendSaveState();

    public boolean sendRestoreState();
}
