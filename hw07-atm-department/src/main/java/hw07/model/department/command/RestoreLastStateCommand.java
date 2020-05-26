package hw07.model.department.command;

import hw07.model.department.internal.AtmObserver;

public class RestoreLastStateCommand extends Command {

    public RestoreLastStateCommand(AtmObserver atmObserver) {
        super(atmObserver);
    }

    @Override
    public boolean execute() {
        atmManager.sendRestoreLastState();
        return true;
    }

}
