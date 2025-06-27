package ru.nsu.bondar.model;

public class StudentTaskResult {
    private Student student;
    private Task task;
    private boolean buildSuccess;
    private boolean docsSuccess;
    private boolean styleSuccess;
    private int passedTests;
    private int failedTests;
    private int skippedTests;
    private int score;
    private int additionalPoints;

    public StudentTaskResult(Student student, Task task) {
        this.student = student;
        this.task = task;

        this.buildSuccess = false;
        this.docsSuccess = false;
        this.styleSuccess = false;
        this.passedTests = 0;
        this.failedTests = 0;
        this.skippedTests = 0;
        this.score = 0;
        this.additionalPoints = 0;
    }

    public Student getStudent() {
        return student;
    }

    public Task getTask() {
        return task;
    }

    public boolean isBuildSuccess() {
        return buildSuccess;
    }

    public boolean isDocsSuccess() {
        return docsSuccess;
    }

    public boolean isStyleSuccess() {
        return styleSuccess;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public int getFailedTests() {
        return failedTests;
    }

    public int getSkippedTests() {
        return skippedTests;
    }

    public int getScore() {
        return score;
    }

    public int getAdditionalPoints() {
        return additionalPoints;
    }

    public void setBuildSuccess(boolean buildSuccess) {
        this.buildSuccess = buildSuccess;
    }

    public void setDocsSuccess(boolean docsSuccess) {
        this.docsSuccess = docsSuccess;
    }

    public void setStyleSuccess(boolean styleSuccess) {
        this.styleSuccess = styleSuccess;
    }

    public void setPassedTests(int passedTests) {
        this.passedTests = passedTests;
    }

    public void setFailedTests(int failedTests) {
        this.failedTests = failedTests;
    }

    public void setSkippedTests(int skippedTests) {
        this.skippedTests = skippedTests;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAdditionalPoints(int additionalPoints) {
        this.additionalPoints = additionalPoints;
    }

    @Override
    public String toString() {
        return "StudentTaskResult{" +
               "student=" + student.getFullName() +
               ", task=" + task.getName() +
               ", buildSuccess=" + buildSuccess +
               ", docsSuccess=" + docsSuccess +
               ", styleSuccess=" + styleSuccess +
               ", passedTests=" + passedTests +
               ", failedTests=" + failedTests +
               ", skippedTests=" + skippedTests +
               ", score=" + score +
               ", additionalPoints=" + additionalPoints +
               '}';
    }
}