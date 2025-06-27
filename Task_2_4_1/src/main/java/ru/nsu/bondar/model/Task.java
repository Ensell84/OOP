package ru.nsu.bondar.model;

import java.time.LocalDate;

public class Task {
    private String id;
    private String name;
    private int maxScore;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;

    public Task(String id, String name, int maxScore, LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = id;
        this.name = name;
        this.maxScore = maxScore;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", maxScore=" + maxScore +
               ", softDeadline=" + softDeadline +
               ", hardDeadline=" + hardDeadline +
               '}';
    }
}