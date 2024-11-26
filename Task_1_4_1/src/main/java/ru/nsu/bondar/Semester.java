package ru.nsu.bondar;

import java.util.List;

public class Semester {
    public enum Status {
        CURRENT,
        COMPLETED
    }

    private int number;
    private Status status;
    private List<Subject> subjects;
}
