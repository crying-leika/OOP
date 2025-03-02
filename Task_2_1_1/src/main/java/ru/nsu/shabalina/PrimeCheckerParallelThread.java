package ru.nsu.shabalina;

import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeCheckerParallelThread {
    /**
     * @param number
     * @return true or false (isPrime)
     */
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    /**
     * @param numbers
     * @return true or false (contains or not prime numbers)
     */
    public static boolean containsNonPrime(int[] numbers, int numThreads) throws InterruptedException {
        AtomicBoolean result = new AtomicBoolean(false);
        Thread[] threads = new Thread[numThreads];
        int chunkSize = (numbers.length + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, numbers.length);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers[j])) {
                        result.set(true);
                        break;
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return result.get();
    }
}

