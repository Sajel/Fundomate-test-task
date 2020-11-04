package fundomate.task1;

import java.math.BigDecimal;

public class CompensationCalculationResult {
    private String partnerName;
    private BigDecimal compensation;

    public CompensationCalculationResult(String partnerName, BigDecimal compensation) {
        this.partnerName = partnerName;
        this.compensation = compensation;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public CompensationCalculationResult setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public BigDecimal getCompensation() {
        return compensation;
    }

    public CompensationCalculationResult setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
        return this;
    }

    @Override
    public String toString() {
        return "CompensationCalculationResult{" +
                "partnerName='" + partnerName + '\'' +
                ", compensation=" + compensation +
                '}';
    }
}
