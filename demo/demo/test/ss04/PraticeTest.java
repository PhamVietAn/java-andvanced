package ss04;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PraticeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isPrime() {
        Pratice prime = new Pratice();
        Assertions.assertTrue(prime.isPrime(-1));
        Assertions.assertTrue(prime.isPrime(0));
        Assertions.assertTrue(prime.isPrime(1));
        Assertions.assertTrue(prime.isPrime(2));
        Assertions.assertTrue(prime.isPrime(3));
        Assertions.assertTrue(prime.isPrime(4));
    }

    @Test
    void fibonacci() {
    }
}