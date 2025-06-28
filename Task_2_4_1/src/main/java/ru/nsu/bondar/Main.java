package ru.nsu.bondar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import ru.nsu.bondar.config.ConfigParser;
import ru.nsu.bondar.dsl.ConfigBuilder;
import ru.nsu.bondar.model.StudentTaskResult;
import ru.nsu.bondar.orchestrator.TaskOrchestrator;
import ru.nsu.bondar.report.ReportBuilder;

/**
 * Main entry point for the OOP Task Checker application.
 * <p>
 * This application parses a config.groovy file from the current directory,
 * processes all student tasks, and generates an HTML report.
 * <p>
 * Requirements:
 * - config.groovy file must exist in current directory
 * - Output will be saved to report.html
 */
public class Main {

    private static final String CONFIG_FILE_NAME = "config.groovy";
    private static final String OUTPUT_FILE_NAME = "report.html";

    public static void main(String[] args) {
        System.out.println("OOP Task Checker Application");
        System.out.println("========================================\n");

        try {
            System.out.println("Step 1: Parsing configuration...");
            ConfigBuilder configBuilder = parseConfiguration();

            System.out.println("Configuration parsed successfully.");
            System.out.println(
                "Found " +
                configBuilder.getTasks().size() +
                " tasks, " +
                configBuilder.getGroups().size() +
                " groups, " +
                configBuilder.getCheckpoints().size() +
                " checkpoints.\n"
            );

            System.out.println("Step 2: Processing tasks for all students...");
            TaskOrchestrator orchestrator = new TaskOrchestrator();
            List<StudentTaskResult> results = orchestrator.processAllTasks(configBuilder);
            System.out.println("Task processing completed.\n");

            System.out.println("Step 3: Generating HTML report...");
            ReportBuilder reportBuilder = new ReportBuilder();
            String htmlReport = reportBuilder.generateHtmlReport(configBuilder, results);

            exportHtmlToFile(htmlReport);

            System.out.println("========================================");
            System.out.println("HTML report exported to: " + OUTPUT_FILE_NAME);
            System.out.println(
                "Open in browser: file://" +
                Paths.get(OUTPUT_FILE_NAME).toAbsolutePath()
            );
            System.out.println("\nTask checking completed successfully! 🎉");
        } catch (Exception e) {
            System.err.println("Error during task checking: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Parses the configuration file using ConfigParser.
     */
    private static ConfigBuilder parseConfiguration() throws Exception {
        File configFile = new File(CONFIG_FILE_NAME);
        if (!configFile.exists()) {
            throw new IllegalStateException(
                "Configuration file '" +
                CONFIG_FILE_NAME +
                "' not found in current directory. " +
                "Please create a config.groovy file with your task configuration."
            );
        }
        return ConfigParser.parse(configFile);
    }

    /**
     * Exports HTML report to a file.
     */
    private static void exportHtmlToFile(String htmlContent) throws IOException {
        File outputFile = new File(OUTPUT_FILE_NAME);

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(htmlContent);
        }

        System.out.println("HTML report saved to: " + outputFile.getAbsolutePath());
    }
}
