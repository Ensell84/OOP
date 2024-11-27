package ru.nsu.bondar;

/**
 * Represents academic subject with type and grade.
 * Tracks if this is final occurrence of subject in study plan.
 */
public class Subject {
    /**
     * Types of subjects exams.
     * Some types affect final grades (others are for intermediate control).
     */
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

    private final String name;
    private final SubjectType type;
    private Integer grade;
    private boolean isFinal;

    /**
     * Creates new subject with name and type.
     * Initially has no grade and not marked as final.
     * @param name subject name
     * @param type type of subject exam
     */
    public Subject(String name, SubjectType type) {
        this.name = name;
        this.type = type;
        this.grade = null;
        this.isFinal = false;
    }

    public String getName() { return name; }
    public SubjectType getType() { return type; }

    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }

    public boolean isFinal() { return isFinal; }
    public void setFinal(boolean isFinal) { this.isFinal = isFinal; }
}
