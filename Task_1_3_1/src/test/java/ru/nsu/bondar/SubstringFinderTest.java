package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Класс, тестирующий SubstringFinder класс.
 */
public class SubstringFinderTest {

    @TempDir
    Path tempDir;

    @Test
    void testBasicFunctionality() throws IOException {
        Path testFile = createTestFile("абракадабра");
        List<Integer> result = SubstringFinder.find(testFile.toString(), "бра");
        assertEquals(List.of(1, 8), result);
    }

    @Test
    void testEmptyFile() throws IOException {
        Path testFile = createTestFile("");
        List<Integer> result = SubstringFinder.find(testFile.toString(), "test");
        assertTrue(result.isEmpty());
    }

    @Test
    void testPatternLargerThanFile() throws IOException {
        Path testFile = createTestFile("abc");
        List<Integer> result = SubstringFinder.find(testFile.toString(), "abcd");
        assertTrue(result.isEmpty());
    }

    @Test
    void testOverlappingPatterns() throws IOException {
        Path testFile = createTestFile("aaaa");
        List<Integer> result = SubstringFinder.find(testFile.toString(), "aa");
        assertEquals(List.of(0, 1, 2), result);
    }

    @Test
    void testChunkBoundaryOverlap() throws IOException {
        int bufferSize = 8192;
        String content = "x".repeat(bufferSize - 2) + "abaaba";

        Path testFile = createTestFile(content);
        List<Integer> result = SubstringFinder.find(testFile.toString(), "aba");

        assertEquals(List.of(bufferSize - 2, bufferSize + 1), result);
    }

    @Test
    void testUnicodeCharacters() throws IOException {
        Path testFile = createTestFile("привет世界こんにちは");
        List<Integer> result = SubstringFinder.find(testFile.toString(), "世界");
        assertEquals(List.of(6), result);
    }

    @Test
    void testLargeFile() throws IOException {
        int sizeInMb = 5000;
        String pattern = "test";
        int totalChunks = (sizeInMb * 1024 * 1024) / pattern.length();

        Path testFile = tempDir.resolve("test.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(testFile)) {
            for (int i = 0; i < totalChunks; i++) {
                if (i % 1000 == 0) {
                    writer.write(pattern);
                } else {
                    writer.write("xxxx");
                }

                if (i % 100000 == 0) {
                    writer.flush();
                }
            }
        }

        List<Integer> result = SubstringFinder.find(testFile.toString(), pattern);

        assertTrue(result.contains(0));
        assertTrue(result.contains(4000));
        assertEquals((totalChunks + 999) / 1000, result.size());
    }

    private Path createTestFile(String content) throws IOException {
        Path filePath = tempDir.resolve("test.txt");
        Files.writeString(filePath, content);
        return filePath;
    }
}