package hw06.atm;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class Money {
    private final static Set<Integer> VALS = ImmutableSet.of(50, 100, 200, 500, 1000, 5000);
    private final int value;

    public Money(int value){
        if( !VALS.contains(value)){
            // TODO replace to user type exception
            throw new RuntimeException("Undefined money type");
        }
        this.value = value;
    }

    public int cost(){
        return value;
    }

    public static Set<Integer> getPossibleVals(){
        return VALS;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (VALS != null ? VALS.hashCode() : 0);
        result = 31 * result + (int)value;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money object = (Money) o;
        return !(value != object.value);
    }
}




