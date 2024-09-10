package ru.nsu.bondar;

import static ru.nsu.bondar.HeapSort.heapSort;

public class Main {
    /**
     * Program entry point.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        int[] array = {12, 11, 13, 5, 6, 7};
        heapSort(array);
        printArray(array);
    }

    /**
     * Prints array to console
     *
     * @param array to print in a1 a3 ... an format
     */
    private static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
