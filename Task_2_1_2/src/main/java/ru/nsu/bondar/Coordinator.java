package ru.nsu.bondar;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.*;
import java.util.concurrent.*;

public class Coordinator {
    private final CoordinationState state = new CoordinationState();
    private ServerSocketChannel serverSocket;
    private ExecutorService executor;

    public void start(int port) throws IOException {
        System.out.println("[Coordinator] Starting on port " + port);
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(port));

        executor = Executors.newVirtualThreadPerTaskExecutor();
        executor.execute(new AcceptorThread(serverSocket, state, executor));
        System.out.println("[Coordinator] Acceptor thread started");
    }

    public boolean checkPrime(long[] array) {
        List<Task> tasks = splitArray(array, 1000);
        System.out.println("[Coordinator] Starting job with " + tasks.size() + " tasks");
        state.startJob(tasks);

        while (!state.isJobComplete()) {
            try {
                Thread.sleep(10); // ??
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        state.isProcessing.set(false);
        return !state.foundNonPrime.get();
    }

    public void stop() throws IOException {
        System.out.println("[Coordinator] Stopping...");
        executor.shutdownNow();

        if (serverSocket != null && serverSocket.isOpen()) {
            serverSocket.close();
        }

        state.closeAllConnections();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate gracefully");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<Task> splitArray(long[] array, int chunkSize) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < array.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, array.length);
            long[] chunk = Arrays.copyOfRange(array, i, end);
            tasks.add(new Task(chunk));
        }
        return tasks;
    }
}
