package edu.northwestern;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;
    private static int count;

    @org.junit.jupiter.api.BeforeAll
    static void beforeClass(){
        System.out.println("This executes before any test cases.  Count = " + count++);
    }

    @org.junit.jupiter.api.BeforeEach
    void setup(){
        account = new BankAccount("Ashley", "Xu", 1000.00, BankAccount.CHECKING);
        System.out.println("Running a test.");
    }

    @org.junit.jupiter.api.Test
     void deposit() {
        double balance = account.deposit(200.00, true);
        assertEquals(1200.00, balance, 0);
    }

    @org.junit.jupiter.api.Test
    void withdraw_branch() {
        double balance = account.withdraw(600.00, true);
        assertEquals(400.00, balance, 0);
    }

    @org.junit.jupiter.api.Test
     void withdraw_notBranch() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(600, false);
        });
    }

    @org.junit.jupiter.api.Test
    void getBalance_deposit() {
        account.deposit(200.00, true);
        assertEquals(1200.00, account.getBalance(), 0);
    }

    @org.junit.jupiter.api.Test
    void getBalance_withdraw() {
        account.withdraw(200.00, true);
        assertEquals(800.00, account.getBalance(), 0);
    }

    @org.junit.jupiter.api.Test
    void isChecking_true(){
        assertTrue(account.isChecking(), "The account is not a checking account");
    }

    @org.junit.jupiter.api.AfterAll
    static void tearClass(){
        System.out.println("This executes after any test cases. Count = " + count++);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown(){
        System.out.println("Count = " + count++);
    }

    //Each row is the set of parameters desired to be tested followed by the expected value.
    private static Stream<Arguments> depositTestParameters(){
        return Stream.of(
                Arguments.of(100.00, 1100.00),
                Arguments.of(200.00, 1200.00),
                Arguments.of(325.14, 1325.14),
                Arguments.of(489.33, 1489.33),
                Arguments.of(1000.00, 2000.00)
        );
    }

    @ParameterizedTest
    @MethodSource("depositTestParameters")
    void deposit(double deposit, double expected) throws Exception {
        double balance = account.deposit(deposit, true);
        assertEquals(expected, balance, .01);
    }

}

