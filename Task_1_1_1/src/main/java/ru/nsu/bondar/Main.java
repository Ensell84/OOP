package ru.nsu.bondar;

import static ru.nsu.bondar.HeapSort.heapSort;

/**
 * Main Class that contains program entry point.
 */
public class Main {
    public static int[] array = {12, 11, 13, 5, 6, 7};

    /**
     * Program entry point.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        heapSort(array);

        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
