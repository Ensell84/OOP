package ru.nsu.bondar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.jetbrains.annotations.NotNull;
import ru.nsu.bondar.Subject.SubjectType;

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
     * File should contain student info in first 4 lines and subjects data after
     * header.
     *
     * @param csvPath path to CSV file in resources folder (must be non-null, annotated with @NotNull)
     * @throws RuntimeException if file is not found or parsing fails
     */
    public void loadFromCsv(@NotNull String csvPath) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/" + csvPath);
        loadFromCsvStream(inputStream);
    }

    /**
     * Loads grade book data from an input stream from CSV file.
     * File should contain student info in first 4 lines and subjects data after
     * header.
     *
     * @param inputStream InputStream from a CSV file (must be non-null, annotated with @NotNull)
     * @throws RuntimeException if file is not found or parsing fails
     */
    public void loadFromCsvStream(@NotNull InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String[] studentGroupRow = reader.readLine().split(",");
            String[] studentNameRow = reader.readLine().split(",");
            String[] isPaidRow = reader.readLine().split(",");
            String[] currentSemesterRow = reader.readLine().split(",");

            this.student = new Student(
                    studentGroupRow[1].trim(),
                    studentNameRow[1].trim(),
                    Boolean.parseBoolean(isPaidRow[1].trim()),
                    Integer.parseInt(currentSemesterRow[1].trim()));

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

                if (!seenSubjects.contains(name) && type == SubjectType.EXAM
                        || type == SubjectType.DIFF_CREDIT) {
                    subject.setFinal(true);
                    seenSubjects.add(name);
                }

                semesters.get(semester - 1).addSubject(subject);
            });
        }
    }

    /**
     * Saves grade book data to a CSV file.
     * File will contain student info in first 4 lines and subjects data after.
     * Config file is saved to output folder.
     *
     * @param configName name of CSV file to save data to
     */
    public void saveToCsv(String configName) throws IOException {
        Path outputDir = Paths.get("output");
        Files.createDirectories(outputDir);
    
        StringBuilder csv = new StringBuilder();
    
        csv.append("studentGroup,").append(student.getStudentId()).append("\n");
        csv.append("studentName,").append(student.getStudentName()).append("\n");
        csv.append("isPaid,").append(student.isPaid()).append("\n");
        csv.append("currentSemester,").append(student.getCurrentSemester()).append("\n");

        csv.append("semester,name,type,grade\n");

        for (Semester semester : semesters) {
            for (Subject subject : semester.getSubjects()) {
                csv.append(semester.getNumber()).append(",")
                        .append(subject.getName()).append(",")
                        .append(subject.getType()).append(",")
                        .append(subject.getGrade() != null ? subject.getGrade() : "null")
                        .append("\n");
            }
        }

        Path outputPath = outputDir.resolve(configName);
        Files.write(outputPath, csv.toString().getBytes());
    }

    /**
     * Retrieves final subjects across all semesters.
     *
     * @return List of final subjects
     */
    public List<Subject> getFinalSubjects() {
        return semesters.stream()
                .flatMap(semester -> semester.getSubjects().stream())
                .filter(Subject::isFinal)
                .toList();
    }

    /**
     * Calculates the average grade for subjects that affect final grades.
     *
     * @return Average grade or null if no graded subjects exist
     */
    public Double calculateAverageGrade() {
        List<Subject> gradedSubjects = semesters.stream()
                .flatMap(semester -> semester.getSubjects().stream())
                .filter(subj -> subj.getType().affectsFinalGrades && subj.getGrade() != null)
                .toList();

        if (gradedSubjects.isEmpty()) {
            return null;
        }

        return gradedSubjects.stream()
                .mapToInt(Subject::getGrade)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates possibility of transfering from Paid to Budget.
     * Transfer is valid, if this conditions for last 2 semesters are true:
     * all exams subjects grade > 3, all differential credit subjects grade > 2, all
     * credit subjects are passed.
     *
     * @return true if possible, otherwise false
     */
    public boolean canTransferToBudget() {
        List<Semester> lastTwoSem = semesters.subList(student.getCurrentSemester() - 3,
                student.getCurrentSemester() - 1);

        boolean examGrade = lastTwoSem.stream()
                .flatMap(sem -> sem.getSubjectsByType(SubjectType.EXAM).stream())
                .filter(subj -> subj.getGrade() != null)
                .anyMatch(subj -> subj.getGrade() < 4);

        boolean diffCreditGrade = lastTwoSem.stream()
                .flatMap(sem -> sem.getSubjectsByType(SubjectType.DIFF_CREDIT).stream())
                .filter(subj -> subj.getGrade() != null)
                .anyMatch(subj -> subj.getGrade() < 3);

        boolean creditGrade = lastTwoSem.stream()
                .flatMap(sem -> sem.getSubjectsByType(SubjectType.CREDIT).stream())
                .filter(subj -> subj.getGrade() != null)
                .anyMatch(subj -> subj.getGrade() == 0);

        return !examGrade && !diffCreditGrade && !creditGrade;
    }

    /**
     * Calculates possibility of getting Red Diploma.
     * It is possible to get Red Diploma, if this conditions are true: 75% of final
     * grades are "5" and there is no "3" final grades, all credits are passed,
     * qualification work grade is "5".
     *
     * <p>Calculation takes care about current state of GradeBook, so it precalculates
     * possibility of getting Red Diploma, even if current semester is not final.
     *
     * @return true if possible, otherwise false
     */
    public boolean canGetRedDiploma() {
        List<Subject> finals = getFinalSubjects().stream()
                .filter(subj -> subj.getGrade() != null)
                .toList();
        long totalGrades = finals.size();

        long goodGrades = finals.stream()
                .filter(subj -> subj.getGrade() == 4)
                .count();

        boolean goodPercentage = (double) goodGrades / totalGrades <= 0.25;

        boolean noUnsatisfactory = finals.stream()
                .noneMatch(subj -> subj.getGrade() < 4);

        boolean allCreditPass = semesters.stream()
                .flatMap(sem -> sem.getSubjectsByType(SubjectType.CREDIT).stream())
                .filter(subj -> subj.getGrade() != null)
                .noneMatch(subj -> subj.getGrade() == 0);

        Integer qualWorkGrade = semesters.getLast()
                .getSubjectsByType(SubjectType.QUALIFICATION_WORK).getFirst().getGrade();
        boolean qualWorkExcellent = qualWorkGrade == null || qualWorkGrade == 5;

        return goodPercentage && noUnsatisfactory && allCreditPass && qualWorkExcellent;
    }

    /**
     * Calculetes possibility of getting increased scolarship.
     * Increased scolarship is paid when average grade >= 4.5.
     *
     * @return true, if possible, otherwise false
     */
    public boolean canReceiveIncreasedScholarship() {
        Double average = calculateAverageGrade();
        return average != null && average >= 4.5;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public Student getStudent() {
        return student;
    }
}
