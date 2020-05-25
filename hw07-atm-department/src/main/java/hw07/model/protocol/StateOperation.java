package hw07.model.protocol;

public interface StateOperation {
    public boolean saveCurrentState();

    public boolean restoreLastState();
}

