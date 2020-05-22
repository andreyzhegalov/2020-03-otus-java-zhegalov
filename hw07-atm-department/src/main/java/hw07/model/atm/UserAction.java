package hw07.model.atm;

public interface UserAction {
    public void put(Object[] banknoteNominals);

    public Object[] get(int summ);
}
