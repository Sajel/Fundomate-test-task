package fundomate.task3;

import java.util.Map;

public class GlassBallCalculationResult {
    private final int result;
    private final Map<Integer, Boolean> steps;

    public GlassBallCalculationResult(int result, Map<Integer, Boolean> steps) {
        this.result = result;
        this.steps = steps;
    }

    public int getResult() {
        return result;
    }

    public Map<Integer, Boolean> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "GlassBallCalculationResult{" +
                "result=" + result +
                ", steps=" + steps +
                '}';
    }
}
