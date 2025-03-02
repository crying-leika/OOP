package ru.nsu.shabalina;

import java.util.concurrent.TimeUnit;

public class PrimeCheckerPerformanceTest {

    public static void main(String[] args) throws InterruptedException {
        int[] numbers = PrimeNumberGenerator.generatePrimes(10000000);

        long startTime = System.nanoTime();
        PrimeCheckerSequential.containsNonPrime(numbers);
        long endTime = System.nanoTime();
        System.out.println("Sequential time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");

        startTime = System.nanoTime();
        PrimeCheckerParallelThread.containsNonPrime(numbers, 4);
        endTime = System.nanoTime();
        System.out.println("Parallel Thread time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");

        startTime = System.nanoTime();
        PrimeCheckerParallelStream.containsNonPrime(numbers);
        endTime = System.nanoTime();
        System.out.println("Parallel Stream time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");
    }
}

