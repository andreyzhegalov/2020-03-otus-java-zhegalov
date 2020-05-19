package hw06.atm;

public class BanknoteCell {
    private final Banknote banknoteType;
    private final int capacity;
    private int occupiedSpace;

    public BanknoteCell(Banknote storageBanknoteType, int capacity) {
        this.banknoteType = storageBanknoteType;
        this.capacity = capacity;
    }

    public Banknote getBanknoteType() {
        return banknoteType;
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
        return banknoteType.getCost() * occupiedSpace;
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
        final int banknoteCnt = neededSum / banknoteType.getCost();
        return banknoteCnt * banknoteType.getCost();
    }

    public void get(int cnt) {
        if (cnt > occupiedSpace)
            throw new RuntimeException("Not enough space in cell");
        occupiedSpace -= cnt;
    }
}
