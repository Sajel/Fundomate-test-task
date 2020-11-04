package fundomate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;

public class CompensationCalculator {

    public Collection<CompensationCalculationResult> calculate(Collection<Partner> topPartners, BigDecimal compensation) {
        return calculatePartners(topPartners, compensation);
    }

    private List<CompensationCalculationResult> calculatePartners(Collection<Partner> subPartners, BigDecimal compensation) {
        return subPartners.stream()
                .map(subPartner -> calculatePartner(subPartner, compensation.multiply(subPartner.getFee())))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Collection<CompensationCalculationResult> calculatePartner(Partner partner, BigDecimal compensation) {
        Collection<Partner> subPartners = partner.getSubPartners();
        if (subPartners == null || subPartners.isEmpty()) {
            return singleton(new CompensationCalculationResult(partner.getName(), compensation));
        }

        validateSumOfFee(subPartners);

        List<CompensationCalculationResult> subPartnersCalculationResults = calculatePartners(subPartners, compensation);
        BigDecimal compensationForPartner = calculatePartnerCompensation(compensation, subPartnersCalculationResults);

        List<CompensationCalculationResult> result = new ArrayList<>(subPartnersCalculationResults.size() + 1);
        result.add(new CompensationCalculationResult(partner.getName(), compensationForPartner));
        result.addAll(subPartnersCalculationResults);
        return result;
    }

    private BigDecimal calculatePartnerCompensation(BigDecimal fullCompensation,
                                                    Collection<CompensationCalculationResult> subPartnersCalculationResults) {
        BigDecimal totalCompensationOfSubPartners = subPartnersCalculationResults.stream()
                .map(CompensationCalculationResult::getCompensation)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return fullCompensation.subtract(totalCompensationOfSubPartners);
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

}
