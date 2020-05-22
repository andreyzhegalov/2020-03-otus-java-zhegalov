package hw06.atm;

public interface UserAction {
    public void put(Object[] banknoteNominals);

    public Object[] get(int summ);
}
