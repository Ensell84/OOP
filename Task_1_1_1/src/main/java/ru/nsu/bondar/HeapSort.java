package ru.nsu.bondar;

/**
 * HeapSort class that implements heapsort sorting algorithm
 */
public class HeapSort {
    /**
     * Sorting function (heapsort algorithm)
     *
     * @param array to sort
     */
    public static void heapSort(int[] array) {
        int n = array.length;

        // Creating heap
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(array, n, i);

        // Picking elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current root to the end
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Calling heapify on reduced heap
            heapify(array, i, 0);
        }
    }

    /**
     * heapify function to maintain the heap property
     *
     * @param array The array representing the heap.
     * @param n The size of the heap.
     * @param i The index of the root of the subtree to be heapified.
     */
    public static void heapify(int[] array, int n, int i) {
        int largest = i; // largest element as root
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child > root
        if (left < n && array[left] > array[largest])
            largest = left;

        // If right child > largest element at the moment
        if (right < n && array[right] > array[largest])
            largest = right;

        // If largest is not root
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            // Recursively heapify affected subtree
            heapify(array, n, largest);
        }
    }
}
