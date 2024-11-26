package ru.nsu.bondar;

public class Subject {
    public enum SubjectType {
        EXAM(true),
        DIFFERENTIATED_CREDIT(true),
        CREDIT(true),
        QUALIFICATION_WORK(true),
        COLLOQUIUM(false),
        CONTROL_WORK(false);

        public final boolean affectsFinalGrades;

        SubjectType(boolean affectsFinalGrades) {
            this.affectsFinalGrades = affectsFinalGrades;
        }
    }

    private String name;
    private SubjectType type;
    private Integer grade;
    private boolean isFinal;
}
