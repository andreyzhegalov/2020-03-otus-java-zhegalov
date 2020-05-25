package hw07.model.department.command;

import hw07.model.department.AtmObserver;

public class RestoreLastStateCommand extends Command {

    public RestoreLastStateCommand(AtmObserver atmManager) {
        super(atmManager);
    }

    @Override
    public boolean execute() {
        atmManager.sendRestoreLastState();
        return true;
    }

}
