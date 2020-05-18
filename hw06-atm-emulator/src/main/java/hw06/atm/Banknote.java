package hw06.atm;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Banknote {
    private final static Set<Integer> POSIBLE_VALS = ImmutableSet.of(50, 100, 200, 500, 1000, 5000);
    private final int cost;

    public Banknote(int value){
        if( !POSIBLE_VALS.contains(value)){
            throw new RuntimeException("Undefined banknote type");
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
    public int hashCode() {
        int result = 17;
        result = 31 * result + (POSIBLE_VALS != null ? POSIBLE_VALS.hashCode() : 0);
        result = 31 * result + (int)cost;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Banknote object = (Banknote) o;
        return !(cost != object.cost);
    }
}




