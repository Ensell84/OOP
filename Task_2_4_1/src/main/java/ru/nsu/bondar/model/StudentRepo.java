package ru.nsu.bondar.model;

public class StudentRepo {
    private Student student;
    private String repositoryUrl;

    public StudentRepo(Student student, String repositoryUrl) {
        this.student = student;
        this.repositoryUrl = repositoryUrl;
    }

    public Student getStudent() {
        return student;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    @Override
    public String toString() {
        return "StudentRepo{" +
               "student=" + student +
               ", repositoryUrl='" + repositoryUrl + '\'' +
               '}';
    }
}