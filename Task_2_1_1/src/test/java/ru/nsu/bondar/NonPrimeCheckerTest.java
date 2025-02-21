package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NonPrimeCheckerTest {

    @Test
    public void testIsPrime() {
        assertFalse(NonPrimeChecker.isPrime(-1));
        assertFalse(NonPrimeChecker.isPrime(0));
        assertFalse(NonPrimeChecker.isPrime(1));
        assertTrue(NonPrimeChecker.isPrime(2));
        assertTrue(NonPrimeChecker.isPrime(3));
        assertFalse(NonPrimeChecker.isPrime(4));
        assertTrue(NonPrimeChecker.isPrime(17));
    }

    @Test
    public void testGenerateLargePrimeArray() {
        int size = 10;
        int[] primes = NonPrimeChecker.generateLargePrimeArray(size);
        assertEquals(size, primes.length);
        for (int prime : primes) {
            assertTrue(NonPrimeChecker.isPrime(prime));
        }
    }

    @Test
    public void testHasNonPrimeSequential_AllPrime() {
        int[] primes = {2, 3, 5, 7, 11, 13};
        assertFalse(NonPrimeChecker.hasNonPrimeSequential(primes));
    }

    @Test
    public void testHasNonPrimeSequential_WithNonPrime() {
        int[] numbers = {2, 3, 4, 5, 7};
        assertTrue(NonPrimeChecker.hasNonPrimeSequential(numbers));
    }

    @Test
    public void testHasNonPrimeParallelThreads_AllPrime() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13};
        assertFalse(NonPrimeChecker.hasNonPrimeParallelThreads(primes, 3));
    }

    @Test
    public void testHasNonPrimeParallelThreads_WithNonPrime() throws InterruptedException {
        int[] numbers = {2, 3, 4, 5, 7};
        assertTrue(NonPrimeChecker.hasNonPrimeParallelThreads(numbers, 3));
    }

    @Test
    public void testHasNonPrimeParallelThreads_InvalidThreads() {
        int[] numbers = {2, 3, 5, 7};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NonPrimeChecker.hasNonPrimeParallelThreads(numbers, 0);
        });
        String expectedMessage = "Number of threads must be positive.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testHasNonPrimeParallelStream_AllPrime() {
        int[] primes = {2, 3, 5, 7, 11, 13};
        assertFalse(NonPrimeChecker.hasNonPrimeParallelStream(primes));
    }

    @Test
    public void testHasNonPrimeParallelStream_WithNonPrime() {
        int[] numbers = {2, 3, 4, 5, 7};
        assertTrue(NonPrimeChecker.hasNonPrimeParallelStream(numbers));
    }
}