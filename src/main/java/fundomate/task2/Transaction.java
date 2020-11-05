package fundomate.task2;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private BigDecimal amount;
    private LocalDateTime executionDateTime;
    private String details;

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public LocalDateTime getExecutionDateTime() {
        return executionDateTime;
    }

    public Transaction setExecutionDateTime(LocalDateTime executionDateTime) {
        this.executionDateTime = executionDateTime;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Transaction setDetails(String details) {
        this.details = details;
        return this;
    }
}
