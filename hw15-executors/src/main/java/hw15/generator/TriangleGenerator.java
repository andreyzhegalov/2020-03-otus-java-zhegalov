package hw15.generator;

import java.util.Optional;

public class TriangleGenerator {
    private final int maxValue;
    private final int startValue;
    private int currentValue;
    private boolean isIncrease = true;

    public TriangleGenerator(int startValue, int maxValue) {
        this.startValue = startValue;
        this.maxValue = maxValue;
        this.currentValue = startValue;
    }

    public Optional<Integer> getNext() {
        if (startValue >= maxValue) {
            return Optional.empty();
        }
        if (currentValue < startValue) {
            return Optional.empty();
        }
        if (currentValue == maxValue) {
            isIncrease = false;
        }

        return isIncrease ? Optional.of(currentValue++) : Optional.of(currentValue--);
    }
}
