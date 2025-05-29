package ru.nsu.bondar;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Coordinator {
    private final WorkerRegistry workerRegistry = new WorkerRegistry();
    private final PendingTaskQueue pendingTaskQueue = new PendingTaskQueue();

    private ServerSocketChannel serverSocket;
    private ExecutorService executor;

    private volatile boolean isProcessing = false;
    private final AtomicBoolean foundNonPrime = new AtomicBoolean(false);
    private final AtomicInteger pendingTasks = new AtomicInteger(0);

    public void start(int port) throws IOException {
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(port));
        executor = Executors.newVirtualThreadPerTaskExecutor();
        executor.execute(new AcceptorThread(serverSocket, workerRegistry));
    }

    public void stop() throws IOException {
        executor.shutdownNow();
        serverSocket.close();
        workerRegistry.closeAll();
    }

    public boolean checkPrime(long[] array) throws InterruptedException {
    }

    public void nonPrimeFound() {
        foundNonPrime.set(true);
        pendingTaskQueue.clear();
    }

    public void taskCompleted() {
        pendingTasks.decrementAndGet();
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    public PendingTaskQueue getPendingTaskQueue() {
        return pendingTaskQueue;
    }

    public WorkerRegistry getWorkerRegistry() {
        return workerRegistry;
    }
}