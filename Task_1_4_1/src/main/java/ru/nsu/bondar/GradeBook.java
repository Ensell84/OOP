package ru.nsu.bondar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.stream.IntStream;

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

    /**
     * Loads grade book data from a CSV file.
     * File should contain student info in first 4 lines and subjects data after header.
     * @param csvPath path to CSV file in resources
     * @throws RuntimeException if file is not found or parsing fails
     */
    public void loadFromCsv(String csvPath) {
        InputStream inputStream = this.getClass().getResourceAsStream("/" + csvPath);
        if (inputStream == null) {
            throw new RuntimeException("Config file not found: " + csvPath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] studentGroupRow = reader.readLine().split(",");
            String[] studentNameRow = reader.readLine().split(",");
            String[] isPaidRow = reader.readLine().split(",");
            String[] currentSemesterRow = reader.readLine().split(",");

            this.student = new Student(
                    studentGroupRow[1].trim(),
                    studentNameRow[1].trim(),
                    Boolean.parseBoolean(isPaidRow[1].trim()),
                    Integer.parseInt(currentSemesterRow[1].trim())
            );

            List<String[]> subjectRows = reader.lines()
                    .skip(1)
                    .map(line -> line.split(","))
                    .toList();

            int totalSemesters = subjectRows.stream()
                    .mapToInt(row -> Integer.parseInt(row[0].trim()))
                    .max()
                    .orElse(0);

            semesters = IntStream.range(1, totalSemesters + 1)
                    .mapToObj(Semester::new)
                    .peek(semester -> {
                        if (semester.getNumber() < student.getCurrentSemester()) {
                            semester.setStatus(Semester.Status.COMPLETED);
                        } else if (semester.getNumber() == student.getCurrentSemester()) {
                            semester.setStatus(Semester.Status.CURRENT);
                        }
                    })
                    .toList();

            Set<String> seenSubjects = new HashSet<>();

            List<String[]> reversedRows = new ArrayList<>(subjectRows);
            Collections.reverse(reversedRows);

            reversedRows.forEach(row -> {
                int semester = Integer.parseInt(row[0].trim());
                String name = row[1].trim();
                Subject.SubjectType type = Subject.SubjectType.valueOf(row[2].trim());

                Integer grade = row.length > 3 && !row[3].trim().equals("null")
                        ? Integer.parseInt(row[3].trim())
                        : null;

                Subject subject = new Subject(name, type);
                subject.setGrade(grade);

                subject.setFinal(!seenSubjects.contains(name));
                seenSubjects.add(name);

                semesters.get(semester - 1).addSubject(subject);
            });

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize grade book: " + e.getMessage(), e);
        }
    }

    public List<Semester> getSemesters() {
        return semesters;
    }
}
