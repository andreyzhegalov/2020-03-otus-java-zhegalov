package hw07.model.department.command;

import hw07.model.department.internal.ChainListener;

public class SaveCurrentStateCommand extends Command {
    @Override
    public void execute(ChainListener listener) {
        listener.sendSaveState();
    }
}
