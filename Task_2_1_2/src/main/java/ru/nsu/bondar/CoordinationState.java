package ru.nsu.bondar;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class CoordinationState {

    public final AtomicBoolean isProcessing = new AtomicBoolean(false);
    public final AtomicBoolean foundNonPrime = new AtomicBoolean(false);
    private final AtomicInteger activeTasks = new AtomicInteger(0);

    public final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    public final Set<Worker> workers = ConcurrentHashMap.newKeySet();

    public void startJob(List<Task> tasks) {
        isProcessing.set(true);
        foundNonPrime.set(false);
        activeTasks.set(0);
        taskQueue.clear();
        taskQueue.addAll(tasks);
    }

    public void signalNonPrimeFound() {
        foundNonPrime.set(true);
        taskQueue.clear();
    }

    public void requeueTask(Task task) {
        if (isProcessing.get() && !foundNonPrime.get()) {
            taskQueue.offer(task);
        }
    }

    public boolean isJobComplete() {
        return (
            !isProcessing.get() ||
            foundNonPrime.get() ||
            (taskQueue.isEmpty() && activeTasks.get() == 0)
        );
    }

    public void registerWorker(Worker worker) {
        workers.add(worker);
    }

    public void unregisterWorker(Worker worker) {
        workers.remove(worker);
    }

    public void taskAssigned() {
        activeTasks.incrementAndGet();
    }

    public void taskCompleted() {
        activeTasks.decrementAndGet();
    }

    public void closeAllConnections() {
        workers.forEach(worker -> {
            try {
                if (worker.channel.isOpen()) {
                    worker.channel.close();
                }
            } catch (IOException ignored) {}
        });
        workers.clear();
    }
}
