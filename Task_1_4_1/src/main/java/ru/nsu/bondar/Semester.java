package ru.nsu.bondar;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents academic semester.
 * Contains list of subjects and semester completion status.
 */
public class Semester {
    /**
     * Represents current state of semester.
     */
    public enum Status {
        CURRENT,
        COMPLETED,
        NOT_STARTED
    }

    private final int number;
    private Status status;
    private List<Subject> subjects;

    /**
     * Creates new semester with semester number.
     * Initial status = NOT_STARTED.
     * @param number semester number
     */
    public Semester(int number) {
        this.number = number;
        this.status = Status.NOT_STARTED;
        this.subjects = new ArrayList<>();
    }

    public int getNumber() { return number; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<Subject> getSubjects() { return subjects; }
    public void addSubject(Subject subject) { subjects.add(subject); }
}
