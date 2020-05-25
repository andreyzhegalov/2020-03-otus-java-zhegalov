package hw07.model.department.command;

import hw07.model.department.AtmManager;

public class SaveCurrentStateCommand extends Command {

    public SaveCurrentStateCommand(AtmManager atmManager) {
        super(atmManager);
    }

    @Override
    public boolean execute() {
        atmManager.sendSaveCurrentState();
        return true;
    }

}
