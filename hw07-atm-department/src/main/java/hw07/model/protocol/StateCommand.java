package hw07.model.protocol;

public interface StateCommand {

    public boolean saveCurrentState();

    public boolean restoreLastState();
}

