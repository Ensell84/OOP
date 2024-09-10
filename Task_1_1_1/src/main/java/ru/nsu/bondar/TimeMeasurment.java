package ru.nsu.bondar;

import java.util.Random;

public class TimeMeasurment {
    /**
     * Function for measuring working time of heapSort function.
     */
    public static void measure() {
        for (int i = 1; i < 1000000000; i+=10000000) {
            int[] array = generateArray(i);
            int[] originalArray = array.clone();

            long time = 0;
            for (int j = 0; j < 3; j++) {
                array = originalArray;
                long startTime = System.nanoTime();
                HeapSort.heapSort(array);
                long endTime = System.nanoTime();
                time += endTime - startTime;
            }
            System.out.println(i + "---" + time/3);
        }
    }

    /**
     * Generates array of random integers.
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
}
