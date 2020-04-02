package hw01.gradle;

import com.google.common.math.IntMath;

class HelloOtus{
    private int value;

    public HelloOtus(int value) {
        this.value = value;
    }

    int getFactorial(){
        return IntMath.factorial(value);
    }
}
