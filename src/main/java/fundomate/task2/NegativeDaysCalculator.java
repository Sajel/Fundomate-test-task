package fundomate.task2;

import java.math.BigDecimal;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class NegativeDaysCalculator {

    public int calculateNegativeDays(Account account, YearMonth wantedYearMonth) {
        List<Transaction> sortedTransaction = account.getTransactions().stream()
                .filter(transaction -> wasTransactionInWantedMonthOrBefore(wantedYearMonth, transaction))
                .sorted(Comparator.comparing(Transaction::getExecutionDateTime))
                .collect(Collectors.toList());

        SortedMap<YearMonth, Collection<Transaction>> transactionByMonth = groupTransactionsByMonth(sortedTransaction);
        BigDecimal balanceBeforeMonth = calculateBalanceBeforeWantedMonth(account, wantedYearMonth, transactionByMonth);

        Collection<Transaction> transactionsOfWantedMonth = transactionByMonth.get(wantedYearMonth);
        if (transactionsOfWantedMonth.isEmpty()) {
            return isBalanceNegative(balanceBeforeMonth) ? wantedYearMonth.lengthOfMonth() : 0;
        }

        SortedMap<MonthDay, Collection<Transaction>> transactionsByDay = groupTransactionsByDay(transactionsOfWantedMonth);

        BigDecimal balance = balanceBeforeMonth;
        int negativeDays = 0;
        for (Collection<Transaction> transactions : transactionsByDay.values()) {
            balance = balance.add(calculateTotalAmount(transactions));
            if (isBalanceNegative(balance)) {
                negativeDays++;
            }
        }
        return negativeDays;
    }

    private BigDecimal calculateTotalAmount(Collection<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private SortedMap<MonthDay, Collection<Transaction>> groupTransactionsByDay(Collection<Transaction> transactionsOfWantedMonth) {
        SortedMap<MonthDay, Collection<Transaction>> transactionsByDay = new TreeMap<>();

        for (Transaction transaction : transactionsOfWantedMonth) {
            MonthDay monthDay = MonthDay.from(transaction.getExecutionDateTime());
            Collection<Transaction> transactionsByMonthDay = transactionsByDay.computeIfAbsent(monthDay, k -> new ArrayList<>());
            transactionsByMonthDay.add(transaction);
        }
        return transactionsByDay;
    }

    private boolean isBalanceNegative(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) < 0;
    }

    private BigDecimal calculateBalanceBeforeWantedMonth(Account account, YearMonth wantedYearMonth, SortedMap<YearMonth, Collection<Transaction>> transactionByMonth) {
        BigDecimal balanceBeforeMonth = account.getInitialBalance();
        for (Collection<Transaction> transactions : transactionByMonth.headMap(wantedYearMonth).values()) {
            for (Transaction transaction : transactions) {
                balanceBeforeMonth = balanceBeforeMonth.add(transaction.getAmount());
            }
        }
        return balanceBeforeMonth;
    }

    private SortedMap<YearMonth, Collection<Transaction>> groupTransactionsByMonth(List<Transaction> sortedTransaction) {
        SortedMap<YearMonth, Collection<Transaction>> transactionByMonths = new TreeMap<>();

        for (Transaction transaction : sortedTransaction) {
            YearMonth transactionYearMonth = YearMonth.from(transaction.getExecutionDateTime());
            Collection<Transaction> transactionsByYearMonth = transactionByMonths.computeIfAbsent(transactionYearMonth, k -> new ArrayList<>());
            transactionsByYearMonth.add(transaction);
        }
        return transactionByMonths;
    }

    private boolean wasTransactionInWantedMonthOrBefore(YearMonth wantedYearMonth, Transaction transaction) {
        return !YearMonth.from(transaction.getExecutionDateTime()).isAfter(wantedYearMonth);
    }
}
