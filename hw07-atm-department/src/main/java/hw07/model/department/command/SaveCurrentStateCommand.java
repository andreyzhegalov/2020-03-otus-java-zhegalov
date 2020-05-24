package hw07.model.department.command;

import java.util.List;

import hw07.model.atm.Atm;
import hw07.model.department.AtmComposite;

public class SaveCurrentStateCommand extends Command {

    public SaveCurrentStateCommand(List<Atm> atmPark) {
        super(atmPark);
    }

    @Override
    public boolean execute() {
        new AtmComposite(atmPark).saveCurrentState();
        return true;
    }

}
