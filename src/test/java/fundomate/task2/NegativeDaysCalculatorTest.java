package fundomate.task2;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static java.time.Month.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NegativeDaysCalculatorTest {

    private final NegativeDaysCalculator negativeDaysCalculator = new NegativeDaysCalculator();

    @Test
    void example_from_task() {
        Account account = new Account()
                .setInitialBalance(BigDecimal.valueOf(1000));

        account.setTransactions(asList(
                createTransaction("10/10/19", 500),
                createTransaction("10/10/19", -900),
                createTransaction("10/10/19", -300),
                createTransaction("10/11/19", 400),
                createTransaction("10/11/19", -900),
                createTransaction("10/12/19", 300),

                createTransaction("11/02/19", 500),
                createTransaction("11/02/19", -650),
                createTransaction("11/04/19", -300),
                createTransaction("11/05/19", 200),
                createTransaction("11/06/19", 400),
                createTransaction("11/06/19", -400),
                createTransaction("11/06/19", -400),

                createTransaction("12/02/19", 400),
                createTransaction("12/02/19", 400),
                createTransaction("12/04/19", -100)
        ));

        int octoberNegativeDays = negativeDaysCalculator.calculateNegativeDays(account, YearMonth.of(2019, OCTOBER));
        int novemberNegativeDays = negativeDaysCalculator.calculateNegativeDays(account, YearMonth.of(2019, NOVEMBER));
        int decemberNegativeDays = negativeDaysCalculator.calculateNegativeDays(account, YearMonth.of(2019, DECEMBER));

        assertAll(
                () -> assertEquals(1, octoberNegativeDays, "Wrong negative days of October"),
                () -> assertEquals(4, novemberNegativeDays, "Wrong negative days of November"),
                () -> assertEquals(0, decemberNegativeDays, "Wrong negative days of December")
        );
    }

    public Transaction createTransaction(String date, long amount) {
        return new Transaction()
                .setExecutionDateTime(LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yy")).atStartOfDay())
                .setAmount(BigDecimal.valueOf(amount));
    }
}