package fundomate;

import fundomate.task1.CompensationCalculationResult;
import fundomate.task1.CompensationCalculator;
import fundomate.task1.Partner;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

class CompensationCalculatorTest {

    private CompensationCalculator compensationCalculator = new CompensationCalculator();

    @Test
    void example_from_the_task() {
        Partner topPartner = createPartner("Top-partner", 0.5);

        Partner subPartner1 = createPartner("Sub-partner1", 0.3);
        Partner subPartner2 = createPartner("Sub-partner2", 0.3);
        Partner subPartner3 = createPartner("Sub-partner3", 0.3);
        topPartner.setSubPartners(asList(subPartner1, subPartner2, subPartner3));


        BigDecimal fullCompensation = BigDecimal.valueOf(1000);
        Collection<CompensationCalculationResult> calculationResults = compensationCalculator.calculate(singleton(topPartner), fullCompensation);

        assertAll(
                () -> assertEquals(4, calculationResults.size(), "Wrong count of calculation result"),
                () -> assertPartnerCompensation(calculationResults, "Top-partner", 50),
                () -> assertPartnerCompensation(calculationResults, "Sub-partner1", 150),
                () -> assertPartnerCompensation(calculationResults, "Sub-partner2", 150),
                () -> assertPartnerCompensation(calculationResults, "Sub-partner3", 150)
        );
    }

    /**
     * Full compensation - 1000$
     * TopPartner 1 - 40% - 400$ - 20$ in the result
     *      Partner 1.1 - 50% - 200$ - 20$ in the result
     *          Partner 1.1.1 - 40% - 80$
     *          Partner 1.1.2 - 50% - 100$
     *      Partner 1.2 - 45% - 180$
     * TopPartner 2 - 40% - 400$ - 40$ in the result
     *      Partner 2.1 - 30% - 120$
     *      Partner 2.2 - 30% - 120$
     *      Partner 2.3 - 30% - 120$
     */
    @Test
    void three_level_hierarchy_example() {
        Partner topPartner1 = createPartner("Top-partner1", 0.4);

        Partner partner1_1 = createPartner("Partner1.1", 0.5);
        Partner partner1_1_1 = createPartner("Partner1.1.1", 0.4);
        Partner partner1_1_2 = createPartner("Partner1.1.2", 0.5);
        partner1_1.setSubPartners(asList(partner1_1_1, partner1_1_2));
        Partner partner1_2 = createPartner("Partner1.2", 0.45);
        topPartner1.setSubPartners(asList(partner1_1, partner1_2));

        Partner topPartner2 = createPartner("Top-partner2", 0.4);
        Partner partner2_1 = createPartner("Partner2.1", 0.3);
        Partner partner2_2 = createPartner("Partner2.2", 0.3);
        Partner partner2_3 = createPartner("Partner2.3", 0.3);
        topPartner2.setSubPartners(asList(partner2_1, partner2_2, partner2_3));


        BigDecimal fullCompensation = BigDecimal.valueOf(1000);
        Collection<CompensationCalculationResult> calculationResults = compensationCalculator.calculate(asList(topPartner1, topPartner2), fullCompensation);

        assertAll(
                () -> assertEquals(9, calculationResults.size(), "Wrong count of calculation result"),
                () -> assertPartnerCompensation(calculationResults, "Top-partner1", 20),
                () -> assertPartnerCompensation(calculationResults, "Partner1.1", 20),
                () -> assertPartnerCompensation(calculationResults, "Partner1.1.1", 80),
                () -> assertPartnerCompensation(calculationResults, "Partner1.1.2", 100),
                () -> assertPartnerCompensation(calculationResults, "Partner1.2", 180),
                () -> assertPartnerCompensation(calculationResults, "Top-partner2", 40),
                () -> assertPartnerCompensation(calculationResults, "Partner2.1", 120),
                () -> assertPartnerCompensation(calculationResults, "Partner2.2", 120),
                () -> assertPartnerCompensation(calculationResults, "Partner2.3", 120)
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
        assertTrue(correctCompensation, format("Wrong calculated compensation for [%s], expected - [%s], actual - [%s]",
                partnerResult.get().getPartnerName(),
                expectedCompensationBigDecimal,
                actualCompensation));
    }

    private Partner createPartner(String name, double fee) {
        return new Partner()
                .setName(name)
                .setFee(BigDecimal.valueOf(fee));
    }
}