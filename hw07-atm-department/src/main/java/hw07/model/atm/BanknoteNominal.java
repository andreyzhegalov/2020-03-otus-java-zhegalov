package hw07.model.atm;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class BanknoteNominal {
    private final static Set<Integer> POSIBLE_VALS = ImmutableSet.of(50, 100, 200, 500, 1000, 5000);
    private final int cost;

    public BanknoteNominal(int value) {
        if( !POSIBLE_VALS.contains(value)){
            throw new AtmException("Undefined banknote nominal " + value);
        }
        this.cost = value;
    }

    public int getCost(){
        return cost;
    }

    public static Set<Integer> getPossibleVals(){
        return POSIBLE_VALS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanknoteNominal object = (BanknoteNominal) o;
        return !(cost != object.cost);
    }
}




