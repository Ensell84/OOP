package ru.nsu.bondar;

import java.nio.ByteBuffer;

public class Task {
    private final long[] numbers;

    public Task(long[] numbers) {
        this.numbers = numbers;
    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(4 + 8 * numbers.length);
        buffer.putInt(numbers.length);
        for (long num : numbers) {
            buffer.putLong(num);
        }
        buffer.flip();
        return buffer;
    }
}