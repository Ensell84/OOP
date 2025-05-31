package ru.nsu.bondar;

import java.nio.channels.SocketChannel;

public class Worker {
    final SocketChannel channel;
    volatile Task currentTask;

    public Worker(SocketChannel channel) {
        this.channel = channel;
    }
}