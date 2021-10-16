package fr.tiagocerqueira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.SecureRandom;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAccount__test_deposit {

    private SecureRandom secureRandom = new SecureRandom();

    private final double INITIAL_AMOUNT = 10_000.0;
    private Account emptyAccount;
    private Account newAccountWithInitialValue;

    private final Logger logger = LogManager.getLogger("testing_logger");

    @BeforeAll
    public static void setUp() throws Exception {
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout("%p\t%d{ISO8601}\t%r\t%c\t[%t]\t%m%n")));
    }

    @Test
    @DisplayName("Testing deposit method for Account")
    public void test__deposit() throws Exception {
        emptyAccount = new Account();
        newAccountWithInitialValue = new Account(INITIAL_AMOUNT);

        assertEquals(0.0, emptyAccount.getBalance());
        assertEquals(INITIAL_AMOUNT, newAccountWithInitialValue.getBalance());
        assertEquals(0, emptyAccount.getOperations().size());
        assertEquals(0, newAccountWithInitialValue.getOperations().size());

        double emptyAmount = (double) (1 + secureRandom.nextInt(1_000));
        double newAmount = (double) (1 + secureRandom.nextInt(1_000));

        emptyAccount.deposit(emptyAmount);
        newAccountWithInitialValue.deposit(newAmount);

        assertEquals(emptyAmount, emptyAccount.getBalance());
        assertEquals(INITIAL_AMOUNT + newAmount, newAccountWithInitialValue.getBalance());
        assertEquals(1, emptyAccount.getOperations().size());
        assertEquals(1, newAccountWithInitialValue.getOperations().size());

        assertThrows(AccountException.class, () -> {
            emptyAccount.deposit(-1000);
        });
        assertEquals(1, emptyAccount.getOperations().size());

        debugAccounts();
    }

    @Test
    @DisplayName("Testing withdrawal method for Account")
    public void test__withdrawal() throws Exception {
        emptyAccount = new Account();
        newAccountWithInitialValue = new Account(INITIAL_AMOUNT);

        assertEquals(0.0, emptyAccount.getBalance());
        assertEquals(INITIAL_AMOUNT, newAccountWithInitialValue.getBalance());
        assertEquals(0, emptyAccount.getOperations().size());
        assertEquals(0, newAccountWithInitialValue.getOperations().size());

        double emptyAmount = secureRandom.nextDouble();
        double newAmount = secureRandom.nextDouble();

        assertThrows(AccountException.class, () -> {
            emptyAccount.withdrawal(emptyAmount);
        });
        assertEquals(0, emptyAccount.getOperations().size());
        newAccountWithInitialValue.withdrawal(newAmount);

        assertEquals(0.0d, emptyAccount.getBalance());
        assertEquals(INITIAL_AMOUNT - newAmount, newAccountWithInitialValue.getBalance());
        assertEquals(1, newAccountWithInitialValue.getOperations().size());

        assertThrows(AccountException.class, () -> {
            emptyAccount.withdrawal(1_000_000);
        });
        assertEquals(0, emptyAccount.getOperations().size());

        debugAccounts();
    }

    private void debugAccounts() {
        if (logger.isDebugEnabled()) {
            logger.debug("test__withdrawal - begin empty account opérations");
            logger.debug(emptyAccount.getHistoryOfOperation());
            logger.debug("test__withdrawal - end empty account opérations\n");

            logger.debug("test__withdrawal - begin account with initial value opérations");
            logger.debug(newAccountWithInitialValue.getHistoryOfOperation());
            logger.debug("test__withdrawal - end account with initial value opérations\n");
        }
    }
}
