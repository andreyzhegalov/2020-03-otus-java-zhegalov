package hw07.model.department.command;

import hw07.model.department.internal.AtmObserver;

public abstract class Command {
    protected final AtmObserver atmObserver;

    public Command(AtmObserver atmManager) {
        this.atmObserver = atmManager;
    }

    public abstract boolean execute();
}
