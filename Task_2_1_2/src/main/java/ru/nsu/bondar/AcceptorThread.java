package ru.nsu.bondar;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
        try {
            while (true) {
                SocketChannel workerChannel = serverChannel.accept();
                Worker worker = new Worker(workerChannel);

                workerRegistry.register(worker);
                executor.execute(new WorkerHandler(worker, workerRegistry));
            }
        } catch (IOException e) {/* ignore */}
    }
}