package ru.nsu.bondar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MainTest {
    @Test
    void testHeapSort() {
        int expected[] = {1,2,3,4,5};
        int actual[] = expected.clone();

        //Arrays.sort(expected);
        HeapSort.heapSort(actual);

        Assertions.assertArrayEquals(expected, actual);
    }
}