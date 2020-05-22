package hw07.model.department;

import java.util.List;

public class GroupAtm implements DepartmentAction {
    private final List<? extends DepartmentAction> atmPark;

    public GroupAtm(List<? extends DepartmentAction> atmPark) {
        this.atmPark = atmPark;
    }

    @Override
    public long getBalance() {
        long result = 0;
        for (final var atm : atmPark) {
            result += atm.getBalance();
        }
        return result;
    }
}
