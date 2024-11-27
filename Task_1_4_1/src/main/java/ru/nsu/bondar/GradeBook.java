package ru.nsu.bondar;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an electronic grade book for a student.
 * Contains information about student, semesters and subjects in study plan.
 * Provides functionality to analyze academic statistics.
 */
public class GradeBook {
    private Student student;
    private List<Semester> semesters;

    public GradeBook() {
        this.semesters = new ArrayList<>();
    }

    public List<Semester> getSemesters() {
        return semesters;
    }
}
