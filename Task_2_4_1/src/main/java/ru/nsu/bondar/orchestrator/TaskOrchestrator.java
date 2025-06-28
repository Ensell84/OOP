package ru.nsu.bondar.orchestrator;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.bondar.dsl.ConfigBuilder;
import ru.nsu.bondar.model.*;
import ru.nsu.bondar.services.*;

/**
 * Orchestrates the task checking process for all students and tasks.
 * This class coordinates the various services to check student repositories,
 * build projects, run tests, and generate results.
 */
public class TaskOrchestrator {

    private final GitService gitService;
    private final BuildService buildService;

    private static final double BUILD_SCORE_RATIO = 0.30;
    private static final double DOCS_SCORE_RATIO = 0.10;
    private static final double STYLE_SCORE_RATIO = 0.10;
    private static final double TESTS_SCORE_RATIO = 0.50;

    public TaskOrchestrator() {
        this.gitService = new GitService();
        this.buildService = new BuildService();
    }

    /**
     * Processes all tasks for all students based on the configuration data.
     *
     * @param config the parsed configuration data
     * @return list of results for all student-task combinations
     */
    public List<StudentTaskResult> processAllTasks(ConfigBuilder config) {
        List<StudentTaskResult> results = new ArrayList<>();

        System.out.println("Starting task processing...");

        for (Group group : config.getGroups()) {
            System.out.println("\n=== PROCESSING GROUP: " + group.getName() + " ===");

            for (Student student : group.getStudents()) {
                for (Task task : config.getTasks()) {
                    System.out.println("\n┌─────────────────────────────────────────────────────────┐");
                    System.out.println(" STUDENT: " + student.getGithubNickname() + "  TASK: " + task.getId());
                    System.out.println("└─────────────────────────────────────────────────────────┘");

                    StudentTaskResult result = processStudentTask(student, task);
                    results.add(result);
                }
            }
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TASK PROCESSING COMPLETED");
        System.out.println("   Total results: " + results.size());
        System.out.println("=".repeat(60));
        return results;
    }

    /**
     * Processes a single task for a single student.
     *
     * @param student the student
     * @param task    the task to check
     * @return the result of checking this task for this student
     */
    private StudentTaskResult processStudentTask(Student student, Task task) {
        StudentTaskResult result = new StudentTaskResult(student, task);

        try {
            String repoPath = cloneStudentRepository(student);
            if (repoPath == null) return result;
            if (!buildStudentProject(result, repoPath, task)) return result;

            generateProjectDocumentation(result, repoPath, task);
            checkProjectCodeStyle(result, repoPath, task);
            runProjectTests(result, repoPath, task);
            calculateFinalScore(result, task);
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println("  RESULT: Processing failed - " + e.getMessage());
        }

        return result;
    }

    private String cloneStudentRepository(Student student) {
        System.out.print("  [1/5] Repository....... ");

        String repoPath = gitService.cloneOrUpdateRepository(
                student.getRepositoryUrl(),
                student.getGithubNickname()
        );

        if (repoPath == null) {
            System.out.println("FAILED");
            System.out.println("  RESULT: Repository clone failed - skipping remaining steps");
            return null;
        }
        return repoPath;
    }

    private boolean buildStudentProject(StudentTaskResult result, String repoPath, Task task) {
        System.out.print("  [2/5] Build............ ");

        boolean buildSuccess = buildService.buildProject(repoPath, task.getId());
        result.setBuildSuccess(buildSuccess);

        if (!buildSuccess) {
            System.out.println("FAILED");
            System.out.println("  RESULT: Build failed - skipping remaining steps");
            return false;
        }
        return true;
    }

    private void generateProjectDocumentation(StudentTaskResult result, String repoPath, Task task) {
        System.out.print("  [3/5] Documentation.... ");

        boolean docsSuccess = buildService.generateDocumentation(repoPath, task.getId());
        result.setDocsSuccess(docsSuccess);

        if (!docsSuccess) System.out.println("FAILED");
    }

    private void checkProjectCodeStyle(StudentTaskResult result, String repoPath, Task task) {
        System.out.print("  [4/5] Style check...... ");

        boolean styleSuccess = buildService.checkCodeStyle(repoPath, task.getId());
        result.setStyleSuccess(styleSuccess);

        if (!styleSuccess) System.out.println("FAILED");
    }

    private void runProjectTests(StudentTaskResult result, String repoPath, Task task) {
        System.out.print("  [5/5] Tests............ ");

        BuildService.TestResults testResults = buildService.runTests(repoPath, task.getId());
        result.setPassedTests(testResults.getPassed());
        result.setFailedTests(testResults.getFailed());
        result.setSkippedTests(testResults.getSkipped());

        int totalTests = testResults.getPassed() + testResults.getFailed();
        System.out.println("OK (" + testResults.getPassed() + "/" + totalTests + " passed)");
    }

    private void calculateFinalScore(StudentTaskResult result, Task task) {
        if (!result.isBuildSuccess()) {
            result.setScore(0);
            System.out.println("  FINAL SCORE: 0/" + task.getMaxScore() + " points");
            return;
        }

        int maxScore = task.getMaxScore();
        double totalScore = 0;

        totalScore += maxScore * BUILD_SCORE_RATIO;

        if (result.isDocsSuccess()) totalScore += maxScore * DOCS_SCORE_RATIO;
        if (result.isStyleSuccess()) totalScore += maxScore * STYLE_SCORE_RATIO;

        int totalTests = result.getPassedTests() + result.getFailedTests();
        if (totalTests > 0) {
            double testRatio = (double) result.getPassedTests() / totalTests;
            totalScore += maxScore * TESTS_SCORE_RATIO * testRatio;
        }

        totalScore += result.getAdditionalPoints();
        int score = Math.min((int) Math.round(totalScore), maxScore);
        result.setScore(score);

        System.out.println("  FINAL SCORE: " + score + "/" + task.getMaxScore() + " points");
    }
}