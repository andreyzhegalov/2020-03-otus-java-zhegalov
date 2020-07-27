package hw15.generator;

import java.util.Optional;

public class TriangleGenerator {
    private final int maxValue;
    private final int startValue;
    private int currentVaue;
    private boolean isIncrease = true;

    public TriangleGenerator(int startValue, int maxValue) {
        this.startValue = startValue;
        this.maxValue = maxValue;
        this.currentVaue = startValue;
    }

    public Optional<Integer> getNext() {
        if (startValue >= maxValue) {
            return Optional.empty();
        }
        if (currentVaue < startValue) {
            return Optional.empty();
        }
        if (currentVaue == maxValue) {
            isIncrease = false;
        }

        return isIncrease ? Optional.of(currentVaue++) : Optional.of(currentVaue--);
    }
}
