package ru.nsu.shabalina;

public class PrimeCheckerSequential {
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
    public static boolean containsNonPrime(int[] numbers) {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}


