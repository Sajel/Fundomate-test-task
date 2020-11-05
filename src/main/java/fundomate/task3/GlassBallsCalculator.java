package fundomate.task3;

import java.util.LinkedHashMap;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GlassBallsCalculator {

    private final GlassBallThrower glassBallThrower;

    public GlassBallsCalculator(GlassBallThrower glassBallThrower) {
        this.glassBallThrower = glassBallThrower;
    }

    public GlassBallCalculationResult calculate() {
        int maxFloor = glassBallThrower.getMaxFloor();
        LinkedHashMap<Integer, Boolean> floorBrokenMap = new LinkedHashMap<>();
        int currentFloor = maxFloor / 2;
        int stepSize = currentFloor;
        int result = maxFloor;
        while (currentFloor > 0 && currentFloor <= maxFloor) {
            boolean broken = isBroken(floorBrokenMap, currentFloor);

            if (broken) {
                //if on first floor ball is broken then this floor is the result
                if (currentFloor == 1) {
                    result = currentFloor;
                    break;
                }
                Boolean previousFloorResult = floorBrokenMap.get(currentFloor - 1);
                //if on current floor ball is broken and is not broken on previous floor then current floor is the result
                if (FALSE.equals(previousFloorResult)) {
                    result = currentFloor;
                    break;
                } else {
                    stepSize = updateStepSize(stepSize);
                    currentFloor = currentFloor - stepSize;
                }
            } else {
                if (currentFloor == maxFloor) {
                    throw new IllegalStateException("Ball is not broken at max floor");
                }
                Boolean nextFloorResult = floorBrokenMap.get(currentFloor + 1);
                //if on current floor ball is NOT broken and is broken on the next floor then next floor is the result
                if (TRUE.equals(nextFloorResult)) {
                    result = currentFloor + 1;
                    break;
                } else {
                    stepSize = updateStepSize(stepSize);
                    currentFloor = currentFloor + stepSize;
                }
            }

        }
        System.out.printf("Result is [%s]\n", result);
        return new GlassBallCalculationResult(result, floorBrokenMap);
    }

    public GlassBallCalculationResult calculateWithTwoBalls() {
        LinkedHashMap<Integer, Boolean> floorBrokenMap = new LinkedHashMap<>();
        int currentFloor = 3;
        int maxFloor = glassBallThrower.getMaxFloor();
        int result = maxFloor;
        while (currentFloor <= maxFloor) {
            boolean broken = isBroken(floorBrokenMap, currentFloor);
            if (broken) {
                if (isBroken(floorBrokenMap, currentFloor - 2)) {
                    result = currentFloor - 2;
                } else if (isBroken(floorBrokenMap, currentFloor - 1)) {
                    result = currentFloor - 1;
                } else {
                    result = currentFloor;
                }
                break;
            } else {
                currentFloor += 3;
            }
        }
        System.out.printf("Result is [%s]\n", result);
        return new GlassBallCalculationResult(result, floorBrokenMap);
    }

    private boolean isBroken(LinkedHashMap<Integer, Boolean> floorBrokenMap, int currentFloor) {
        boolean broken = glassBallThrower.throwBall(currentFloor);
        floorBrokenMap.put(currentFloor, broken);
        return broken;
    }

    private int updateStepSize(int stepSize) {
        return stepSize == 1 ? 2 : stepSize / 2;
    }
}
