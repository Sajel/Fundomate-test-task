package fundomate.task3;

import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GlassBallsCalculator {

    private final GlassBallThrower glassBallThrower;

    public GlassBallsCalculator(GlassBallThrower glassBallThrower) {
        this.glassBallThrower = glassBallThrower;
    }

    public int calculate() {
        int maxFloor = glassBallThrower.getMaxFloor();
        int currentFloor = maxFloor / 2;
        SortedMap<Integer, Boolean> floorBrokenMap = new TreeMap<>();
        int stepSize = currentFloor;
        while (currentFloor > 0 && currentFloor <= maxFloor) {
            boolean broken = glassBallThrower.throwBall(currentFloor);
            floorBrokenMap.put(currentFloor, broken);
            System.out.printf("Throwing from [%s], broken - [%s]\n", currentFloor, broken);
            if(broken) {
                if(currentFloor == 1) {
                    System.out.printf("Result is [%s]\n", currentFloor);
                    return currentFloor;
                }
                Boolean previousFloorResult = floorBrokenMap.get(currentFloor - 1);
                if(FALSE.equals(previousFloorResult)) {
                    System.out.printf("Result is [%s]\n", currentFloor);
                    return currentFloor;
                } else {
                    stepSize = stepSize == 1 ? stepSize + 1  : stepSize / 2;
                    currentFloor = currentFloor - stepSize;
                }
            } else {
                if(currentFloor == maxFloor) {
                    System.out.printf("Result is [%s]\n", currentFloor);
                    return currentFloor;
                }
                Boolean nextFloorResult = floorBrokenMap.get(currentFloor + 1);
                if(TRUE.equals(nextFloorResult)) {
                    System.out.printf("Result is [%s]\n", currentFloor + 1);
                    return currentFloor + 1;
                } else {
                    stepSize = stepSize == 1 ? stepSize + 1 : stepSize / 2;
                    currentFloor = currentFloor + stepSize;
                }
            }

        }
        return maxFloor;
    }
}
