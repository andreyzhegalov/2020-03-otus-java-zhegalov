package hw07.model.department;

import java.util.ArrayList;
import java.util.List;

import hw07.model.atm.Atm;

public class Department implements AtmHandler {
    private final List<Atm> atmPark = new ArrayList<>();
    private final List<Subscriber> subscribers = new ArrayList<>();

    public long getBalance() {
        return new AtmComposite(atmPark).getBalance();
    }

    @Override
    public boolean addAtm(Atm newAtm) {
        subscribers.add(newAtm);
        return atmPark.add(newAtm);
    }

    public void saveAtmState(){
        for (final var subscriber : subscribers) {
            subscriber.saveState();
        }
    }

    public void restoreAtmState(){
        for (final var subscriber : subscribers) {
            subscriber.restoreState();
        }
    }
}
