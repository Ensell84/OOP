package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GradeBookTest {

    private GradeBook gradeBook;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        gradeBook = new GradeBook();
    }

    @Test
    void testLoadFromCsvStream() throws IOException {
        String csvContent = createCsvContent(2,
                new SubjectData(1, "Calculus I", "EXAM", 4, true),
                new SubjectData(1, "Linear Algebra", "EXAM", 5, true),
                new SubjectData(2, "Calculus II", "EXAM", 4, true),
                new SubjectData(2, "Physics", "EXAM", 3, true));

        Path csvPath = tempDir.resolve("test.csv");
        Files.writeString(csvPath, csvContent);

        try (InputStream inputStream = Files.newInputStream(csvPath)) {
            gradeBook.loadFromCsvStream(inputStream);
        }

        assertEquals(2, gradeBook.getSemesters().size());
        assertEquals("FIT-23213", gradeBook.getStudent().getStudentId());
        assertEquals("Ivan Ivanov", gradeBook.getStudent().getStudentName());
        assertTrue(gradeBook.getStudent().isPaid());
        assertEquals(2, gradeBook.getStudent().getCurrentSemester());

        List<Subject> subjectsInSem1 = gradeBook.getSemesters().get(0).getSubjects();
        assertEquals(2, subjectsInSem1.size());
        assertTrue(subjectsInSem1.stream().anyMatch(subj -> Objects.equals(subj.getName(), "Calculus I")));
        assertTrue(subjectsInSem1.stream().noneMatch(subj -> subj.getGrade() == 3));
        assertTrue(subjectsInSem1.stream().allMatch(Subject::isFinal));
    }

    @Test
    void testGetFinalSubjects() throws IOException {
        String csvContent = createCsvContent(3,
                new SubjectData(1, "Calculus I", "EXAM", 4, true),
                new SubjectData(2, "Calculus II", "EXAM", 5, true),
                new SubjectData(3, "Probability Theory", "EXAM", null, true));

        Path csvPath = tempDir.resolve("test.csv");
        Files.writeString(csvPath, csvContent);

        try (InputStream inputStream = Files.newInputStream(csvPath)) {
            gradeBook.loadFromCsvStream(inputStream);
        }

        List<Subject> finalSubjects = gradeBook.getFinalSubjects();
        assertEquals(3, finalSubjects.size());
        assertEquals("Calculus I", finalSubjects.get(0).getName());
        assertEquals("Calculus II", finalSubjects.get(1).getName());
        assertEquals("Probability Theory", finalSubjects.get(2).getName());
    }

    @Test
    void testCalculateAverageGrade() throws IOException {
        String csvContent = createCsvContent(2,
                new SubjectData(1, "Math", "EXAM", 4, true),
                new SubjectData(1, "Physics", "EXAM", 5, true),
                new SubjectData(2, "Chemistry", "EXAM", 3, true),
                new SubjectData(2, "Biology", "CREDIT", 2, false));

        Path csvPath = tempDir.resolve("test.csv");
        Files.writeString(csvPath, csvContent);

        try (InputStream inputStream = Files.newInputStream(csvPath)) {
            gradeBook.loadFromCsvStream(inputStream);
        }

        Double average = gradeBook.calculateAverageGrade();
        assertEquals(3.5, average);
    }

    @Test
    void testCanTransferToBudget() throws IOException {
        String csvContent = createCsvContent(3,
                new SubjectData(1, "Math", "EXAM", 4, true),
                new SubjectData(1, "Physics", "EXAM", 5, true),
                new SubjectData(2, "Chemistry", "EXAM", 4, true),
                new SubjectData(2, "Programming", "DIFF_CREDIT", 4, true),
                new SubjectData(3, "Biology", "CREDIT", 1, false));

        Path csvPath = tempDir.resolve("test.csv");
        Files.writeString(csvPath, csvContent);

        try (InputStream inputStream = Files.newInputStream(csvPath)) {
            gradeBook.loadFromCsvStream(inputStream);
        }

        assertTrue(gradeBook.canTransferToBudget());
    }

    @Test
    void testCanGetRedDiploma() throws IOException {
        String csvContent = createCsvContent(4,
                new SubjectData(1, "Math", "EXAM", 5, true),
                new SubjectData(1, "Physics", "EXAM", 5, true),
                new SubjectData(2, "Chemistry", "EXAM", 5, true),
                new SubjectData(2, "Programming", "DIFF_CREDIT", 5, true),
                new SubjectData(3, "Biology", "CREDIT", 1, false),
                new SubjectData(4, "Qualification Work", "QUALIFICATION_WORK", 5, true));

        Path csvPath = tempDir.resolve("test.csv");
        Files.writeString(csvPath, csvContent);

        try (InputStream inputStream = Files.newInputStream(csvPath)) {
            gradeBook.loadFromCsvStream(inputStream);
        }

        assertTrue(gradeBook.canGetRedDiploma());
    }

    @Test
    void testCanReceiveIncreasedScholarship() throws IOException {
        String csvContent = createCsvContent(2,
                new SubjectData(1, "Math", "EXAM", 5, true),
                new SubjectData(1, "Physics", "EXAM", 5, true),
                new SubjectData(2, "Chemistry", "EXAM", 4, true));

        Path csvPath = tempDir.resolve("test.csv");
        Files.writeString(csvPath, csvContent);

        try (InputStream inputStream = Files.newInputStream(csvPath)) {
            gradeBook.loadFromCsvStream(inputStream);
        }

        assertTrue(gradeBook.canReceiveIncreasedScholarship());
    }

    private String createCsvContent(int currentSemester, SubjectData... subjects) {
        StringBuilder sb = new StringBuilder();
        sb.append("studentGroup,FIT-23213\n");
        sb.append("studentName,Ivan Ivanov\n");
        sb.append("isPaid,true\n");
        sb.append("currentSemester," + currentSemester + "\n");
        sb.append("semester,name,type,grade\n");

        for (SubjectData subject : subjects) {
            sb.append(subject.semester + "," + subject.name + "," + subject.type + ","
                    + (subject.grade != null ? subject.grade : "null") + "\n");
        }

        return sb.toString();
    }

    private static class SubjectData {
        int semester;
        String name;
        String type;
        Integer grade;
        boolean isFinal;

        SubjectData(int semester, String name, String type, Integer grade, boolean isFinal) {
            this.semester = semester;
            this.name = name;
            this.type = type;
            this.grade = grade;
            this.isFinal = isFinal;
        }
    }
}