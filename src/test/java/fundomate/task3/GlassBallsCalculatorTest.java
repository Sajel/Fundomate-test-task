package fundomate.task3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class GlassBallsCalculatorTest {

    @Test
    void specific() {
        GlassBallsCalculator glassBallsCalculator = new GlassBallsCalculator(new GlassBallThrower(3, 100));
        assertEquals(3, glassBallsCalculator.calculate().getResult());
    }

    @Test
    void full_test() {
        Map<Integer, Integer> floorStepsMap = new TreeMap<>();
        for (int i = 1; i <= 100; i++) {
            System.out.println("================================== " + i + " ==================================");
            GlassBallsCalculator glassBallsCalculator = new GlassBallsCalculator(new GlassBallThrower(i, 100));
            GlassBallCalculationResult calculationResult = glassBallsCalculator.calculate();
            assertEquals(i, calculationResult.getResult());
            floorStepsMap.put(i, calculationResult.getSteps().size());
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, Integer> entry : floorStepsMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
        int max = floorStepsMap.values().stream().mapToInt(it -> it).max().getAsInt();
        System.out.println("Max - " + max);
        int min = floorStepsMap.values().stream().mapToInt(it -> it).min().getAsInt();
        System.out.println("Min - " + min);
        double average = floorStepsMap.values().stream().mapToInt(it -> it).average().getAsDouble();
        System.out.println("Average - " + average);
    }

    @Test
    void full_test_with_two_balls() {
        Map<Integer, Integer> floorStepsMap = new TreeMap<>();
        for (int i = 1; i <= 100; i++) {
            System.out.println("================================== " + i + " ==================================");
            GlassBallsCalculator glassBallsCalculator = new GlassBallsCalculator(new LimitedGlassBallThrower(2, i, 100));
            GlassBallCalculationResult calculationResult = glassBallsCalculator.calculateWithTwoBalls();
            assertEquals(i, calculationResult.getResult());
            floorStepsMap.put(i, calculationResult.getSteps().size());
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, Integer> entry : floorStepsMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
        int max = floorStepsMap.values().stream().mapToInt(it -> it).max().getAsInt();
        System.out.println("Max - " + max);
        int min = floorStepsMap.values().stream().mapToInt(it -> it).min().getAsInt();
        System.out.println("Min - " + min);
        double average = floorStepsMap.values().stream().mapToInt(it -> it).average().getAsDouble();
        System.out.println("Average - " + average);
    }
}