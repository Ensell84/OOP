package ru.nsu.bondar.dsl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import ru.nsu.bondar.config.ConfigParser

import java.nio.file.Path
import java.time.LocalDate

import static org.junit.jupiter.api.Assertions.*

class ConfigParserTest {

    @TempDir
    Path tempDir

    File configFile

    @BeforeEach
    void setUp() {
        configFile = tempDir.resolve("test-config.groovy").toFile()
    }

    @Test
    void testSimpleTaskParsing() {
        def configContent = '''
            tasks {
                task {
                    id = "TEST_001"
                    name = "Test Task"
                    maxScore = 100
                    softDeadline "2025-03-01"
                    hardDeadline "2025-03-15"
                }
            }
        '''
        configFile.text = configContent

        def result = ConfigParser.parse(configFile)

        assertNotNull(result)
        assertEquals(1, result.getTasks().size())

        def task = result.getTasks().get(0)
        assertEquals("TEST_001", task.getId())
        assertEquals("Test Task", task.getName())
        assertEquals(100, task.getMaxScore())
        assertEquals(LocalDate.parse("2025-03-01"), task.getSoftDeadline())
        assertEquals(LocalDate.parse("2025-03-15"), task.getHardDeadline())
    }

    @Test
    void testSimpleGroupParsing() {
        def configContent = '''
            groups {
                group {
                    name = "TestGroup"
                    student {
                        githubNickname = "testuser"
                        fullName = "Test User"
                        repositoryUrl = "https://github.com/testuser/repo"
                    }
                }
            }
        '''
        configFile.text = configContent

        def result = ConfigParser.parse(configFile)

        assertNotNull(result)
        assertEquals(1, result.getGroups().size())

        def group = result.getGroups().get(0)
        assertEquals("TestGroup", group.getName())
        assertEquals(1, group.getStudents().size())

        def student = group.getStudents().get(0)
        assertEquals("testuser", student.getGithubNickname())
        assertEquals("Test User", student.getFullName())
        assertEquals("https://github.com/testuser/repo", student.getRepositoryUrl())
    }

    @Test
    void testCheckpointParsing() {
        def configContent = '''
            checkpoints {
                checkpoint {
                    name = "Midterm"
                    date "2025-05-01"
                }
            }
        '''
        configFile.text = configContent

        def result = ConfigParser.parse(configFile)

        assertNotNull(result)
        assertEquals(1, result.getCheckpoints().size())

        def checkpoint = result.getCheckpoints().get(0)
        assertEquals("Midterm", checkpoint.getName())
        assertEquals(LocalDate.parse("2025-05-01"), checkpoint.getDate())
    }

    @Test
    void testCompleteConfigParsing() {
        def configContent = '''
            tasks {
                task {
                    id = "2_1_1"
                    name = "Simple Numbers"
                    maxScore = 10
                    softDeadline "2025-03-01"
                    hardDeadline "2025-03-15"
                }
                task {
                    id = "2_3_1"
                    name = "Snake Game"
                    maxScore = 20
                    softDeadline "2025-04-01"
                    hardDeadline "2025-04-15"
                }
            }

            groups {
                group {
                    name = "12345"
                    student {
                        githubNickname = "student1"
                        fullName = "Student One"
                        repositoryUrl = "https://github.com/student1/repo"
                    }
                    student {
                        githubNickname = "student2"
                        fullName = "Student Two"
                        repositoryUrl = "https://github.com/student2/repo"
                    }
                }
            }

            checkpoints {
                checkpoint {
                    name = "Midterm"
                    date "2025-05-01"
                }
            }
        '''
        configFile.text = configContent

        def result = ConfigParser.parse(configFile)

        assertNotNull(result)
        assertEquals(2, result.getTasks().size())
        assertEquals("2_1_1", result.getTasks().get(0).getId())
        assertEquals("2_3_1", result.getTasks().get(1).getId())

        assertEquals(1, result.getGroups().size())
        def group = result.getGroups().get(0)
        assertEquals("12345", group.getName())
        assertEquals(2, group.getStudents().size())
        assertEquals("student1", group.getStudents().get(0).getGithubNickname())
        assertEquals("student2", group.getStudents().get(1).getGithubNickname())

        assertEquals(1, result.getCheckpoints().size())
        assertEquals("Midterm", result.getCheckpoints().get(0).getName())
    }

    @Test
    void testEmptyConfig() {
        def configContent = '''
            tasks {
            }
            groups {
            }
            checkpoints {
            }
        '''
        configFile.text = configContent

        def result = ConfigParser.parse(configFile)

        assertNotNull(result)
        assertEquals(0, result.getTasks().size())
        assertEquals(0, result.getGroups().size())
        assertEquals(0, result.getCheckpoints().size())
    }

    @Test
    void testFileNotFound() {
        def nonExistentFile = new File("nonexistent.groovy")
        assertThrows(FileNotFoundException.class) {
            ConfigParser.parse(nonExistentFile)
        }
    }

    @Test
    void testInvalidSyntax() {
        def configContent = '''
            tasks {
                task {
                    id = "TEST"
                    invalidField = "this should cause error"
                }
            }
        '''
        configFile.text = configContent

        assertThrows(Exception.class) {
            ConfigParser.parse(configFile)
        }
    }
}
