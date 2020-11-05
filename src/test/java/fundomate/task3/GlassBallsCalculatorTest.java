package fundomate.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlassBallsCalculatorTest {

    @Test
    void specific() {
        GlassBallsCalculator glassBallsCalculator = new GlassBallsCalculator(new GlassBallThrower(3, 100));
        assertEquals(3, glassBallsCalculator.calculate());
    }

    @Test
    void full_test() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("================================== " + i + " ==================================");
            GlassBallsCalculator glassBallsCalculator = new GlassBallsCalculator(new GlassBallThrower(i, 100));
            assertEquals(i, glassBallsCalculator.calculate());
        }
    }
}