package ru.nsu.bondar;

import java.util.Arrays;
import java.util.Random;

/**
 * A class that provides methods to check if a number is prime and to check if an array of numbers
 * contains a non-prime number.
 */
public class NonPrimeChecker {
    /**
     * Checks if a number is prime.
     *
     * @param num the number to check
     * @return true if the number is prime, false otherwise
     */
    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates an array of large prime numbers.
     *
     * @param size the size of the array
     * @return the array of prime numbers
     */
    public static int[] generateLargePrimeArray(int size) {
        int[] primes = new int[size];
        int count = 0;
        int num = 2;
        while (count < size) {
            if (isPrime(num)) {
                primes[count++] = num;
            }
            num++;
        }

        Random rand = new Random();
        for (int i = primes.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = primes[i];
            primes[i] = primes[j];
            primes[j] = temp;
        }

        return primes;
    }

    /**
     * Checks if an array of numbers contains a non-prime number sequentially.
     *
     * @param numbers the array of numbers to check
     * @return true if the array contains a non-prime number, false otherwise 
     */
    public static boolean hasNonPrimeSequential(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an array of numbers contains a non-prime number using multiple threads.
     *
     * @param numbers the array of numbers to check
     * @param numThreads the number of threads to use
     * @return true if the array contains a non-prime number, false otherwise
     */
    public static boolean hasNonPrimeParallelThreads(int[] numbers, int numThreads)
            throws InterruptedException {
        if (numThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive.");
        }
        int arrayLength = numbers.length;
        if (arrayLength == 0) {
            return false;
        }

        Thread[] threads = new Thread[numThreads];
        boolean[] threadResults = new boolean[numThreads];

        int chunkSize = (arrayLength + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, arrayLength);
            int[] chunk = Arrays.copyOfRange(numbers, start, end);

            threads[i] = new Thread(() -> {
                boolean hasNonPrimeInChunk = false;
                for (int num : chunk) {
                    if (!isPrime(num)) {
                        hasNonPrimeInChunk = true;
                        break;
                    }
                }
                threadResults[threadIndex] = hasNonPrimeInChunk;
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (boolean result : threadResults) {
            if (result) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if an array of numbers contains a non-prime number using parallel streams.
     *
     * @param numbers the array of numbers to check
     * @return true if the array contains a non-prime number, false otherwise
     */
    public static boolean hasNonPrimeParallelStream(int[] numbers) {
        return Arrays.stream(numbers)
                .parallel()
                .anyMatch(num -> !isPrime(num));
    }

    /**
     * Main method to test the performance of the three methods.
     */
    public static void main(String[] args) throws InterruptedException {
        int[] primeNumbers = generateLargePrimeArray(1000000);

        System.out.println("Array size: " + primeNumbers.length);

        // Sequential
        hasNonPrimeSequential(primeNumbers);

        long startTimeSeq = System.nanoTime();
        boolean hasNonPrimeSeq = hasNonPrimeSequential(primeNumbers);
        long endTimeSeq = System.nanoTime();

        long durationSeq = (endTimeSeq - startTimeSeq) / 1_000_000;
        System.out.println("Sequential execution: non-prime? "
                + hasNonPrimeSeq + ", Time: " + durationSeq + " ms");

        // Threads
        System.out.println("\nParallel execution with Threads:");
        for (int numThreads = 1; numThreads <= 36; numThreads++) {
            hasNonPrimeParallelThreads(primeNumbers, numThreads);

            long startTimeThread = System.nanoTime();
            boolean hasNonPrimeThread = hasNonPrimeParallelThreads(primeNumbers, numThreads);
            long endTimeThread = System.nanoTime();

            long durationThread = (endTimeThread - startTimeThread) / 1_000_000;
            System.out.println("Threads = " + numThreads + ": non-prime? "
                    + hasNonPrimeThread + ", Time: " + durationThread + " ms");
        }

        // ParallelStream
        hasNonPrimeParallelStream(primeNumbers);

        long startTimeStream = System.nanoTime();
        boolean hasNonPrimeStream = hasNonPrimeParallelStream(primeNumbers);
        long endTimeStream = System.nanoTime();

        long durationStream = (endTimeStream - startTimeStream) / 1_000_000;
        System.out.println("\nParallel Stream execution: non-prime? "
                + hasNonPrimeStream + ", Time: " + durationStream + " ms");
    }
}