package hw07.model.department.command;

import hw07.model.department.internal.AtmObserver;

public abstract class Command {
    protected final AtmObserver atmManager;

    public Command(AtmObserver atmManager) {
        this.atmManager = atmManager;
    }

    public abstract boolean execute();
}
