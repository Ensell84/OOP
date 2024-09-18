package ru.nsu.bondar;

import static ru.nsu.bondar.HeapSort.heapSort;

import java.util.Scanner;

/**
 * Main Class that contains program entry point.
 */
public class Main {
    public static int[] array;

    /**
     * Program entry point.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int size = scanner.nextInt();
        array = new int[size];

        System.out.println("Enter elements:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }

        heapSort(array);

        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
