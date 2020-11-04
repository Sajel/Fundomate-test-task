package fundomate;

import java.math.BigDecimal;
import java.util.Collection;

public class Partner {
    private String name;
    private BigDecimal fee;
    private Collection<Partner> subPartners;

    public String getName() {
        return name;
    }

    public Partner setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Partner setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public Collection<Partner> getSubPartners() {
        return subPartners;
    }

    public Partner setSubPartners(Collection<Partner> subPartners) {
        this.subPartners = subPartners;
        return this;
    }
}
