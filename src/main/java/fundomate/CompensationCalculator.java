package fundomate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CompensationCalculator {

    public Collection<CompensationCalculationResult> calculate(Collection<Partner> topPartners, BigDecimal compensation) {
        validateSumOfFee(topPartners);

        Collection<CompensationCalculationResult> result = new ArrayList<>();
        for (Partner topPartner : topPartners) {
            BigDecimal fee = topPartner.getFee();
            BigDecimal compensationForPartner = compensation.multiply(fee);
            Collection<CompensationCalculationResult> calculationResults = calculate(topPartner, compensationForPartner);
            result.addAll(calculationResults);
        }
        return result;
    }

    private void validateSumOfFee(Collection<Partner> topPartners) {
        BigDecimal sumOfFee = topPartners.stream()
                .map(Partner::getFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sumOfFee.compareTo(BigDecimal.valueOf(1)) >= 1) {
            String exceptionMessage = String.format("Sum of partners' fees is [%s], should be not above [1]", sumOfFee);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private Collection<CompensationCalculationResult> calculate(Partner partner, BigDecimal compensation) {
        Collection<Partner> subPartners = partner.getSubPartners();
        if (subPartners == null || subPartners.isEmpty()) {
//            System.out.printf("Compensation for [%s] is [%s]", partner.getName(), compensation);
            CompensationCalculationResult calculationResult = new CompensationCalculationResult()
                    .setCompensation(compensation)
                    .setPartnerName(partner.getName());
            return Collections.singleton(calculationResult);
        }

        validateSumOfFee(subPartners);


        BigDecimal compensationForPartner = compensation;
        List<CompensationCalculationResult> result = new ArrayList<>();

        for (Partner subPartner : subPartners) {
            Collection<CompensationCalculationResult> subPartnerCalculationResults = calculate(subPartner, compensation.multiply(subPartner.getFee()));
            result.addAll(subPartnerCalculationResults);
            BigDecimal subPartnerFullCompensation = subPartnerCalculationResults.stream()
                    .map(CompensationCalculationResult::getCompensation)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            compensationForPartner = compensationForPartner.subtract(subPartnerFullCompensation);
        }
        CompensationCalculationResult partnerCalculationResult = new CompensationCalculationResult()
                .setCompensation(compensationForPartner)
                .setPartnerName(partner.getName());
        result.add(0, partnerCalculationResult);
        return result;
//         subPartners.stream()
//                .map(subPartner -> calculate(subPartner, compensation.multiply(subPartner.getFee())))
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
    }
}
