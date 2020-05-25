package hw07.model.department.command;

import hw07.model.department.AtmManager;

public abstract class Command {
    protected final AtmManager atmManager;

    public Command(AtmManager atmManager) {
        this.atmManager = atmManager;
    }

    public abstract boolean execute();
}
