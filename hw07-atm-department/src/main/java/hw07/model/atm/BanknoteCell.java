package hw07.model.atm;

public class BanknoteCell implements CellPrototype {
    private final BanknoteNominal banknoteNominal;
    private final int capacity;
    private int occupiedSpace;

    public BanknoteCell(BanknoteNominal storageBanknoteType, int capacity) {
        this.banknoteNominal = storageBanknoteType;
        this.capacity = capacity;
    }

    @Override
    public CellPrototype clone() {
        final var cell = new BanknoteCell(banknoteNominal, capacity);
        cell.setOccupiedSpace(this.occupiedSpace);
        return cell;
    }

    public BanknoteNominal getBanknoteNominal() {
        return banknoteNominal;
    }

    public int getOccupiedSpace() {
        return occupiedSpace;
    }

    public void setOccupiedSpace(int cnt) {
        occupiedSpace = cnt;
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

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (banknoteNominal != null ? banknoteNominal.hashCode() : 0);
        result = 31 * result + (int) capacity;
        result = 31 * result + (int) occupiedSpace;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BanknoteCell object = (BanknoteCell) o;

        if (banknoteNominal != null ? !banknoteNominal.equals(object.banknoteNominal) : object.banknoteNominal != null)
            return false;
        if (capacity != object.capacity)
            return false;
        return !(occupiedSpace != object.occupiedSpace);
    }

}
