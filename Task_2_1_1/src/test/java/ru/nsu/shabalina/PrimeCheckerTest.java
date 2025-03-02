package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeCheckerTest {

    @Test
    public void testSequential() {
        int[] numbers = PrimeNumberGenerator.generatePrimes(1000);
        assertFalse(PrimeCheckerSequential.containsNonPrime(numbers));
    }

    @Test
    public void testParallelThread() throws InterruptedException {
        int[] numbers = PrimeNumberGenerator.generatePrimes(1000);
        assertFalse(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testParallelStream() {
        int[] numbers = PrimeNumberGenerator.generatePrimes(1000);
        assertFalse(PrimeCheckerParallelStream.containsNonPrime(numbers));
    }

    @Test
    public void testNonPrime() throws InterruptedException {
        int[] numbers = {4, 6, 8, 9, 10};
        assertTrue(PrimeCheckerSequential.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testEmptyArray() throws InterruptedException {
        int[] numbers = {};
        assertFalse(PrimeCheckerSequential.containsNonPrime(numbers));
        assertFalse(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertFalse(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testSinglePrime() throws InterruptedException {
        int[] numbers = {2};
        assertFalse(PrimeCheckerSequential.containsNonPrime(numbers));
        assertFalse(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertFalse(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testSingleNonPrime() throws InterruptedException {
        int[] numbers = {4}; // 4 — составное число
        assertTrue(PrimeCheckerSequential.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testLargeArray() throws InterruptedException {
        int[] numbers = PrimeNumberGenerator.generatePrimes(10000);
        assertFalse(PrimeCheckerSequential.containsNonPrime(numbers));
        assertFalse(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertFalse(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testNegativeNumbers() throws InterruptedException {
        int[] numbers = {-1, -2, -3, -4};
        assertTrue(PrimeCheckerSequential.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }

    @Test
    public void testZeroAndOne() throws InterruptedException {
        int[] numbers = {0, 1};
        assertTrue(PrimeCheckerSequential.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelStream.containsNonPrime(numbers));
        assertTrue(PrimeCheckerParallelThread.containsNonPrime(numbers, 4));
    }
}

