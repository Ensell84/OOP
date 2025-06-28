package ru.nsu.bondar.dsl

import ru.nsu.bondar.model.Checkpoint
import ru.nsu.bondar.model.Group
import ru.nsu.bondar.model.Student
import ru.nsu.bondar.model.Task
import java.time.LocalDate

class ConfigurationDelegate {
    List<Task> tasks = []
    List<Group> groups = []
    List<Checkpoint> checkpoints = []

    def task(@DelegatesTo(value = TaskConfig, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def taskConfig = new TaskConfig()
        closure.delegate = taskConfig
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()

        tasks << new Task(taskConfig.id, taskConfig.name, taskConfig.maxScore, taskConfig.softDeadline, taskConfig.hardDeadline)
    }

    def group(@DelegatesTo(value = GroupConfig, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def groupConfig = new GroupConfig()
        closure.delegate = groupConfig
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()

        def group = new Group(groupConfig.name)
        groupConfig.students.each { studentConfig ->
            def student = new Student(
                studentConfig.githubNickname,
                studentConfig.fullName,
                studentConfig.repositoryUrl
            )
            group.addStudent(student)
        }
        groups << group
    }

    def checkpoint(@DelegatesTo(value = CheckpointConfig, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def checkpointConfig = new CheckpointConfig()
        closure.delegate = checkpointConfig
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()

        checkpoints << new Checkpoint(checkpointConfig.name, checkpointConfig.date)
    }

    static class TaskConfig {
        String id
        String name
        int maxScore
        LocalDate softDeadline
        LocalDate hardDeadline

        void softDeadline(String dateStr) {
            this.softDeadline = LocalDate.parse(dateStr)
        }

        void hardDeadline(String dateStr) {
            this.hardDeadline = LocalDate.parse(dateStr)
        }
    }

    static class GroupConfig {
        String name
        List<StudentConfig> students = []

        def student(@DelegatesTo(value = StudentConfig, strategy = Closure.DELEGATE_ONLY) Closure closure) {
            def studentConfig = new StudentConfig()
            closure.delegate = studentConfig
            closure.resolveStrategy = Closure.DELEGATE_ONLY
            closure()

            students << studentConfig
        }
    }

    static class StudentConfig {
        String githubNickname
        String fullName
        String repositoryUrl
    }

    static class CheckpointConfig {
        String name
        LocalDate date

        void date(String dateStr) {
            this.date = LocalDate.parse(dateStr)
        }
    }
}
