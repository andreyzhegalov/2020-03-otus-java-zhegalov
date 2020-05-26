package hw07.model.department.command;

import hw07.model.department.internal.AtmObserver;

public class SaveCurrentStateCommand extends Command {

    public SaveCurrentStateCommand(AtmObserver atmObserver) {
        super(atmObserver);
    }

    @Override
    public boolean execute() {
        atmManager.sendSaveCurrentState();
        return true;
    }

}
