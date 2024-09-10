package ru.nsu.bondar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class MainTest {

    /**
     * Generates array of random integers for testing.
     *
     * @param length The length of the array to generate.
     * @return An array of random integers.
     */
    private static int[] generateArray(int length) {
        Random rd = new Random();
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            array[i] = rd.nextInt();
        }

        return array;
    }

    @Test
    void testHeapSort() {
        for (int i = 0; i < 1000; i++) {
            int[] expected = generateArray(i);
            int[] actual = expected.clone();

            Arrays.sort(expected);
            HeapSort.heapSort(actual);

            Assertions.assertArrayEquals(expected, actual, "Array is not sorted properly");
        }
    }

    @Test
    void testHeapify() {
        int[] array = generateArray(100);
        int n = array.length;

        // start from the last non-leaf node
        for (int i = n / 2 - 1; i > 0 ; i--) {
            HeapSort.heapify(array, n, i);
            Assertions.assertTrue(isMaxHeap(array, n, i), "The subtree rooted at index " + i + " not satisfy the max-heap property.");
        }
    }

    /**
     * Checks if the subtree rooted at index 'i' satisfies the max-heap property.
     *
     * @param array The heap array.
     * @param n     The size of the heap.
     * @param i     The index of the current node.
     * @return True if the subtree is a max-heap.
     */
    private boolean isMaxHeap(int[] array, int n, int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Base case: if leaf node, return true
        if (i >= n / 2) {
            return true;
        }

        // Check if current node is greater than its left child
        if (left < n && array[i] < array[left]) {
            return false;
        }

        // Check if current node is greater than its right child
        if (right < n && array[i] < array[right]) {
            return false;
        }

        // Recursively check left and right subtrees
        return isMaxHeap(array, n, left) && isMaxHeap(array, n, right);
    }

}