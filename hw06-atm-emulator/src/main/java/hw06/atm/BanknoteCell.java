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

    public void put(int cnt) {
        if (occupiedSpace + cnt > capacity) {
            throw new RuntimeException("No free space");
        }
        occupiedSpace += cnt;
    }

    public void get(int cnt) {
        if (cnt > occupiedSpace)
            throw new RuntimeException("Not enough in cell");
        occupiedSpace -= cnt;
    }

    public int tryGetSum(int sum) {
        if (isEmpty()) {
            return 0;
        }
        final int cnt = sum / getBanknoteType().getCost();
        if (cnt == 0) {
            return 0;
        }
        return (getOccupiedSpace() >= cnt) ? cnt : getOccupiedSpace();
    }

    public boolean tryPut(int cnt) {
        return !isFull() && getFreeSpace() >= cnt;
    }
}
