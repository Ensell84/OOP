package ru.nsu.bondar.model;

public class Student {
    private String githubNickname;
    private String fullName;

    public Student(String githubNickname, String fullName) {
        this.githubNickname = githubNickname;
        this.fullName = fullName;
    }
    
    public String getGithubNickname() {
        return githubNickname;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Student{" +
               "githubNickname='" + githubNickname + '\'' +
               ", fullName='" + fullName + '\'' +
               '}';
    }
}