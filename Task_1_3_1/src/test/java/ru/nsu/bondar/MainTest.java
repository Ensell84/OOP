package ru.nsu.bondar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SubstringFinderTest {

    @TempDir
    Path tempDir;

    @Test
    void testMain() throws IOException {
        Path testFile = createTestFile("абаба");

        String input = testFile.toString() + "\nаба\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        assertEquals(List.of(0, 2), Main.result);
    }

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
        String content = "x".repeat(bufferSize-2) + "abaaba";

        Path testFile = createTestFile(content);
        List<Integer> result = SubstringFinder.find(testFile.toString(), "aba");

        assertEquals(List.of(bufferSize-2, bufferSize+1), result);
    }

    @Test
    void testUnicodeCharacters() throws IOException {
        Path testFile = createTestFile("привет世界こんにちは");
        List<Integer> result = SubstringFinder.find(testFile.toString(), "世界");
        assertEquals(List.of(6), result);
    }

    @Test
    void testNullInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> SubstringFinder.find(null, "test"));
        assertThrows(IllegalArgumentException.class,
                () -> SubstringFinder.find("test.txt", null));
    }

    @Test
    void testEmptyPattern() {
        assertThrows(IllegalArgumentException.class,
                () -> SubstringFinder.find("test.txt", ""));
    }

    private Path createTestFile(String content) throws IOException {
        Path filePath = tempDir.resolve("test.txt");
        Files.writeString(filePath, content);
        return filePath;
    }
}