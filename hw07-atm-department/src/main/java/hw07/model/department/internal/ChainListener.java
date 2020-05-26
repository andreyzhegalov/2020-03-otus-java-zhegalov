package hw07.model.department.internal;

public interface ChainListener {
    public void setNext(ChainListener nextListener);

    public long summarizeBallance(long initBalance);

    public boolean sendSaveState();

    public boolean sendRestoreState();
}
