package hw06.atm;

public class MoneyCell {
    private final Money moneyType;
    private final int capacity;
    private int totalCnt;

    public Money getMoneyType() {
        return moneyType;
    }

    public MoneyCell(Money storageMoneyType, int capacity) {
        this.moneyType = storageMoneyType;
        this.capacity = capacity;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public boolean isFull() {
        return capacity == totalCnt;
    }

    public int freeSpace() {
        return capacity - totalCnt;
    }

    public void put(int cnt) {
        if (totalCnt + cnt > capacity) {
            throw new RuntimeException("No free space");
        }
        totalCnt += cnt;
    }

    public void get(int cnt) {
        if (cnt > totalCnt)
            throw new RuntimeException("Not enough money");
        totalCnt -= cnt;
    }

}
