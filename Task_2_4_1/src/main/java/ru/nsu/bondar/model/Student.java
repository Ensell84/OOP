package ru.nsu.bondar.model;

public class Student {

    private String githubNickname;
    private String fullName;
    private String repositoryUrl;

    public Student(
        String githubNickname,
        String fullName,
        String repositoryUrl
    ) {
        this.githubNickname = githubNickname;
        this.fullName = fullName;
        this.repositoryUrl = repositoryUrl;
    }

    public String getGithubNickname() {
        return githubNickname;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    @Override
    public String toString() {
        return (
            "Student{" +
            "githubNickname='" +
            githubNickname +
            '\'' +
            ", fullName='" +
            fullName +
            '\'' +
            ", repositoryUrl='" +
            repositoryUrl +
            '\'' +
            '}'
        );
    }
}
