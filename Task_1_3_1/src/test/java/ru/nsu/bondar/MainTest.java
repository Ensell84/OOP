package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Класс, тестирующий Main класс.
 */
public class MainTest {
    private final InputStream standardIn = System.in;

    @TempDir
    Path tempDir;

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        Main.result = null;
    }

    @Test
    void testMain() throws IOException {
        Path testFile = createTestFile("абаба");
        String input = testFile.toString() + "\nаба\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        assertEquals(List.of(0, 2), Main.result);
    }

    @Test
    void testMainFileNotFound() {
        String input = "nonexistent.txt\ntest\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        assertNull(Main.result);
    }

    @Test
    void testMainEmptyPattern() {
        String input = "test.txt\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
        assertNull(Main.result);
    }

    private Path createTestFile(String content) throws IOException {
        Path filePath = tempDir.resolve("test.txt");
        Files.writeString(filePath, content);
        return filePath;
    }
}