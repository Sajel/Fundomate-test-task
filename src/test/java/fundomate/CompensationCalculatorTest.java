package fundomate;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompensationCalculatorTest {

    private CompensationCalculator compensationCalculator = new CompensationCalculator();

    @Test
    void example_from_the_task() {
        Partner topPartner = createPartner("Top-partner", 0.5);

        Partner subPartner1 = createPartner("Sub-partner1", 0.3);
        Partner subPartner2 = createPartner("Sub-partner2", 0.3);
        Partner subPartner3 = createPartner("Sub-partner3", 0.3);

        topPartner.setSubPartners(Arrays.asList(subPartner1, subPartner2, subPartner3));


        Collection<CompensationCalculationResult> calculationResults = compensationCalculator.calculate(singleton(topPartner), BigDecimal.valueOf(1000));

        assertAll(
                () -> assertPartnerCompensation(calculationResults, "Top-partner", 50),
                () -> assertPartnerCompensation(calculationResults, "Sub-partner1", 150),
                () -> assertPartnerCompensation(calculationResults, "Sub-partner2", 150),
                () -> assertPartnerCompensation(calculationResults, "Sub-partner3", 150)
        );
    }

    private void assertPartnerCompensation(Collection<CompensationCalculationResult> calculationResults,
                                           String partnerName,
                                           Number expectedCompensation) {
        Optional<CompensationCalculationResult> partnerResult = calculationResults.stream()
                .filter(result -> result.getPartnerName().equals(partnerName))
                .findAny();
        assertTrue(partnerResult.isPresent(), format("There is no calculation result for partner [%s]", partnerName));

        BigDecimal expectedCompensationBigDecimal = BigDecimal.valueOf(expectedCompensation.doubleValue());
        BigDecimal actualCompensation = partnerResult.get().getCompensation();
        boolean correctCompensation = expectedCompensationBigDecimal.compareTo(actualCompensation) == 0;
        assertTrue(correctCompensation, format("Wrong calculated compensation, expected - [%s], actual - [%s]", expectedCompensationBigDecimal, actualCompensation));
    }

    private Partner createPartner(String name, double fee) {
        return new Partner()
                .setName(name)
                .setFee(BigDecimal.valueOf(fee));
    }
}