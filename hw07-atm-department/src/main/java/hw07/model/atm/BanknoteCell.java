package hw07.model.atm;

public class BanknoteCell {
    private final BanknoteNominal banknoteNominal;
    private final int capacity;
    private int occupiedSpace;

    public BanknoteCell(BanknoteNominal storageBanknoteType, int capacity) {
        this.banknoteNominal = storageBanknoteType;
        this.capacity = capacity;
    }

    public BanknoteNominal getBanknoteNominal() {
        return banknoteNominal;
    }

    public int getOccupiedSpace() {
        return occupiedSpace;
    }

    public boolean isFull() {
        return capacity == occupiedSpace;
    }

    public boolean isEmpty() {
        return occupiedSpace == 0;
    }

    public int getFreeSpace() {
        return capacity - occupiedSpace;
    }

    public int getBalance() {
        return banknoteNominal.getCost() * occupiedSpace;
    }

    public boolean tryPut(int cnt) {
        return !isFull() && getFreeSpace() >= cnt;
    }

    public void put(int cnt) {
        occupiedSpace += cnt;
    }

    public int tryGetSum(int sum) {
        if (isEmpty()) {
            return 0;
        }
        final int neededSum = (getBalance() > sum) ? sum : getBalance();
        final int banknoteCnt = neededSum / banknoteNominal.getCost();
        return banknoteCnt * banknoteNominal.getCost();
    }

    public void get(int cnt) {
        if (cnt < 0)
            throw new IllegalArgumentException("Get banknote count must be positive. Now is " + cnt);
        if (cnt > occupiedSpace)
            throw new AtmException("Not enough space in cell");
        occupiedSpace -= cnt;
    }
}
