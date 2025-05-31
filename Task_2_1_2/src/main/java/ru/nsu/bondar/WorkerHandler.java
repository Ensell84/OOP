package ru.nsu.bondar;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WorkerHandler implements Runnable {

    private static final int BUFFER_SIZE = 16;
    private static final long WORKER_TIMEOUT_MS = 30000;

    private final Worker worker;
    private final CoordinationState state;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private long lastActivityTime = System.currentTimeMillis();

    public WorkerHandler(Worker worker, CoordinationState state) {
        this.worker = worker;
        this.state = state;
    }

    @Override
    public void run() {
        try {
            worker.channel.socket().setSoTimeout(1000);
            System.out.println(
                "[WorkerHandler] Started for " +
                worker.channel.getRemoteAddress()
            );

            while (!Thread.interrupted() && worker.channel.isOpen()) {
                checkTimeout();

                if (worker.currentTask == null) {
                    assignTask();
                }
            }
        } catch (Exception e) {
            System.err.println("[WorkerHandler] Error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void checkTimeout() {
        long now = System.currentTimeMillis();
        if (now - lastActivityTime > WORKER_TIMEOUT_MS) {
            throw new RuntimeException("Worker timeout");
        }
    }

    private void assignTask() throws IOException {
        if (!state.isProcessing.get()) return;

        Task task = state.taskQueue.poll();
        if (task != null) {
            worker.currentTask = task;
            state.taskAssigned();
            System.out.println("[WorkerHandler] Task assigned (queue size: " + state.taskQueue.size() + ")");
            ByteBuffer taskBuffer = task.toByteBuffer();

            worker.channel.write(taskBuffer);

            lastActivityTime = System.currentTimeMillis();
        }
    }

    private void processInput() throws IOException {
        SocketChannel channel = worker.channel;

        try {
            int bytesRead = channel.read(buffer);

            if (bytesRead == -1) {
                throw new IOException("Worker disconnected");
            }

            if (bytesRead > 0) {
                lastActivityTime = System.currentTimeMillis();
                buffer.flip();

                while (buffer.remaining() >= 1) {
                    byte result = buffer.get();

                    if (result == 0) {
                        System.out.println("[WorkerHandler] Non-prime found, stopping job");
                        state.signalNonPrimeFound();
                    }

                    if (worker.currentTask != null) {
                        state.taskCompleted();
                        worker.currentTask = null;
                        System.out.println("[WorkerHandler] Task completed");
                    }
                }

                buffer.compact();
            }
        } catch (SocketTimeoutException ignore) {}
    }

    private void cleanup() {
        if (worker.currentTask != null) {
            state.requeueTask(worker.currentTask);
            worker.currentTask = null;
            System.out.println("[WorkerHandler] Task requeued during cleanup");
        }

        state.unregisterWorker(worker);
        System.out.println("[WorkerHandler] Worker disconnected");
        closeChannel();
    }

    private void closeChannel() {
        try {
            if (worker.channel.isOpen()) {
                worker.channel.close();
            }
        } catch (IOException ignored) {}
    }
}
