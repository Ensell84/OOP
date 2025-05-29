package ru.nsu.bondar;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
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

    }

    public void stop() throws IOException {

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