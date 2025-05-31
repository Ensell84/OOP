package ru.nsu.bondar;

import java.io.IOException;
import java.nio.channels.*;
import java.util.concurrent.ExecutorService;

public class AcceptorThread implements Runnable {
    private final ServerSocketChannel serverChannel;
    private final CoordinationState state;
    private final ExecutorService executor;

    public AcceptorThread(ServerSocketChannel serverChannel, CoordinationState state, ExecutorService executor) {
        this.serverChannel = serverChannel;
        this.state = state;
        this.executor = executor;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                try {
                    SocketChannel workerChannel = serverChannel.accept();
                    workerChannel.configureBlocking(true);
                    System.out.println("[AcceptorThread] Worker connected: " + workerChannel.getRemoteAddress());

                    Worker worker = new Worker(workerChannel);
                    state.registerWorker(worker);

                    executor.execute(new WorkerHandler(worker, state));

                } catch (ClosedChannelException e) {
                    break;
                } catch (IOException e) {
                    if (!serverChannel.isOpen()) break;
                    System.err.println("[AcceptorThread] IO error: " + e.getMessage());
                }
            }
        } finally {
            System.out.println("[AcceptorThread] Shutting down");
            closeServer();
        }
    }

    private void closeServer() {
        try {
            if (serverChannel.isOpen()) {
                serverChannel.close();
            }
        } catch (IOException ignored) {}
    }
}