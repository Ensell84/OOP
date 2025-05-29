package ru.nsu.bondar;

public class Main {
    public static void main(String[] args) throws Exception {
        Coordinator coordinator = new Coordinator();
        coordinator.start(8080);

        long[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10};

        System.out.println("Starting");
        boolean allPrime = coordinator.checkPrime(numbers);
        System.out.println("All prime? " + allPrime);

        coordinator.stop();
    }
}