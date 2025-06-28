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
    private final CheckingService checkingService;

    private static final double BUILD_SCORE_RATIO = 0.30;
    private static final double DOCS_SCORE_RATIO = 0.10;
    private static final double STYLE_SCORE_RATIO = 0.10;
    private static final double TESTS_SCORE_RATIO = 0.50;

    public TaskOrchestrator() {
        this.gitService = new GitService();
        this.buildService = new BuildService();
        this.checkingService = new CheckingService();
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
                    System.out.println(
                            "\n┌─────────────────────────────────────────────────────────┐"
                    );
                    System.out.println(
                            " STUDENT: " +
                                    student.getGithubNickname() +
                                    "  TASK: " +
                                    task.getId()
                    );
                    System.out.println(
                            "└─────────────────────────────────────────────────────────┘"
                    );

                    StudentTaskResult result = processStudentTask(
                            student,
                            task
                    );
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
}