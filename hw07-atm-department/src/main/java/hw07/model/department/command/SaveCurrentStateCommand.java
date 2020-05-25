package hw07.model.department.command;

import hw07.model.department.AtmObserver;

public class SaveCurrentStateCommand extends Command {

    public SaveCurrentStateCommand(AtmObserver atmManager) {
        super(atmManager);
    }

    @Override
    public boolean execute() {
        atmManager.sendSaveCurrentState();
        return true;
    }

}
