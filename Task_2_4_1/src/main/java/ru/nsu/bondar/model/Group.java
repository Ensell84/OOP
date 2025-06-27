package ru.nsu.bondar.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<Student> students;

    public Group(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Group{" +
               "name='" + name + '\'' +
               ", students=" + students +
               '}';
    }
}