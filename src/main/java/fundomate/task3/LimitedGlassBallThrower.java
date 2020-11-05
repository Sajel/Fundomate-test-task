package fundomate.task3;

import static java.lang.String.format;

public class LimitedGlassBallThrower extends GlassBallThrower {
    private final int maxAmountOfBalls;
    private int ballsWasted = 0;

    public LimitedGlassBallThrower(int maxAmountOfBalls, int minBreakingFloor, int maxFloor) {
        super(minBreakingFloor, maxFloor);
        this.maxAmountOfBalls = maxAmountOfBalls;
    }

    @Override
    public boolean throwBall(int floor) {
        if (ballsWasted == maxAmountOfBalls) {
            throw new IllegalStateException(format("You've wasted all [%s] balls", ballsWasted));
        }
        boolean broken = super.throwBall(floor);
        if (broken) {
            ballsWasted++;
        }
        return broken;
    }
}
