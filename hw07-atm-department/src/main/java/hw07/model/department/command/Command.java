package hw07.model.department.command;

import java.util.List;

import hw07.model.atm.Atm;

public abstract class Command {
    protected final List<Atm> atmPark;

    public Command(List<Atm> atmPark) {
        this.atmPark = atmPark;
    }

    public abstract boolean execute();
}


