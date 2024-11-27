package ru.nsu.bondar;

/**
 * Represents a student and his academic status.
 */
public class Student {
    private final String group;
    private final String name;
    private boolean isPaid;
    private int currentSemester;

    /**
     * Creates new student with parameters.
     * @param group student group id
     * @param name student full name
     * @param isPaid true if student is not on budget
     * @param currentSemester current semester number
     */
    public Student(String group, String name, boolean isPaid, int currentSemester) {
        this.group = group;
        this.name = name;
        this.isPaid = isPaid;
        this.currentSemester = currentSemester;
    }

    public String getStudentId() { return group; }
    public String getStudentName() { return name; }
    public boolean isPaid() { return isPaid; }
    public int getCurrentSemester() { return currentSemester; }
}