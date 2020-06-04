package hw07.model.department.command;

import hw07.model.department.internal.ChainListener;

public abstract class Command {
    public abstract void execute(ChainListener listener);
}
