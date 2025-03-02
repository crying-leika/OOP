package ru.nsu.shabalina;

import java.util.Random;

public class PrimeNumberGenerator {

    public static int[] generatePrimes(int count) {
        int[] primes = new int[count];
        Random random = new Random();
        int number = 2;
        for (int i = 0; i < count; ) {
            if (isPrime(number)) {
                primes[i] = number;
                i++;
            }
            number = random.nextInt(1000000) + 2;
        }
        return primes;
    }

    private static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}

