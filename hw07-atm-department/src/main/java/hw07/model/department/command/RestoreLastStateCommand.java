package hw07.model.department.command;

import hw07.model.department.AtmManager;

public class RestoreLastStateCommand extends Command {

    public RestoreLastStateCommand(AtmManager atmManager) {
        super(atmManager);
    }

    @Override
    public boolean execute() {
        atmManager.sendRestoreLastState();
        return true;
    }

}
