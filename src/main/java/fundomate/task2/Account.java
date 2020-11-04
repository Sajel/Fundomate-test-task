package fundomate.task2;

import java.math.BigDecimal;
import java.util.Collection;

public class Account {

    private String name;
    private BigDecimal initialBalance;
    private Collection<Transaction> transactions;

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public Account setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
        return this;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public Account setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }
}
