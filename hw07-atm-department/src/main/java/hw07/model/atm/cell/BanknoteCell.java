package hw07.model.atm.cell;

import hw07.model.atm.AtmException;
import hw07.model.atm.BanknoteNominal;
import java.util.ArrayList;
import java.util.List;

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
        if( getFreeSpace() < cnt){
            throw new AtmException("Not enough space in cell");
        }
        occupiedSpace += cnt;
    }

    public long tryGetSum(long sum) {
        if (isEmpty()) {
            return 0;
        }
        final long neededSum = (getBalance() > sum) ? sum : getBalance();
        final long banknoteCnt = neededSum / banknoteNominal.getCost();
        return banknoteCnt * banknoteNominal.getCost();
    }

    public List<BanknoteNominal> get( int cnt) {
        if (cnt < 0)
            throw new AtmException("Get banknote count must be positive. Now is " + cnt);
        if (cnt > occupiedSpace)
            throw new AtmException("Not enough space in cell");
        final List<BanknoteNominal> result = new ArrayList<>();
        final int banknoteCost = getBanknoteNominal().getCost();
        occupiedSpace -= cnt;
        for (int i = 0; i < cnt; i++) {
            try {
                result.add(new BanknoteNominal(banknoteCost));
            } catch (Exception e) {
                throw new AtmException("Internal error. Create banknote with nominal:" + banknoteCost);
            }
        }
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
