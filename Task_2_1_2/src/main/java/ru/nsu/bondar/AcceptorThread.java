package ru.nsu.bondar;

import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcceptorThread implements Runnable {
    private final ServerSocketChannel serverChannel;
    private final WorkerRegistry workerRegistry;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public AcceptorThread(ServerSocketChannel serverChannel, WorkerRegistry workerRegistry) {
        this.serverChannel = serverChannel;
        this.workerRegistry = workerRegistry;
    }

    @Override
    public void run() {
    }
}