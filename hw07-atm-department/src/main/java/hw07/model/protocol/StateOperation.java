package hw07.model.protocol;

public interface StateOperation extends Operation{
    public boolean saveCurrentState();

    public boolean restoreLastState();
}

