package fundomate.task3;

import static java.lang.String.format;

public class GlassBallThrower {

    private final int minBreakingFloor;
    private final int maxFloor;

    public GlassBallThrower(int minBreakingFloor, int maxFloor) {
        this.minBreakingFloor = minBreakingFloor;
        this.maxFloor = maxFloor;
    }

    public boolean throwBall(int floor) {
        if (floor > maxFloor || floor <= 0) {
            throw new IllegalArgumentException(format("Wrong floor value: [%s], should be in [1;%s] interval", floor, maxFloor));
        }
        return floor >= minBreakingFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }
}
