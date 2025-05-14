package com.example.ccwc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WcToolTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private final String testFilePath = "src/test/resources/test.txt";

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testFileExists() {
        File file = new File(testFilePath);
        assertTrue("Test file should exist", file.exists());
    }

    @Test
    public void testByteCount() throws Exception {
        // Get the actual byte count of the file
        Path path = Paths.get(testFilePath);
        byte[] bytes = Files.readAllBytes(path);
        int expectedByteCount = bytes.length;

        // Run the wc tool with -c option
        WcTool.main(new String[]{"-c", testFilePath});

        // Check the output
        String output = outContent.toString().trim();
        String expectedOutput = expectedByteCount + " " + testFilePath;
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testLineCount() throws Exception {
        // Get the actual line count of the file
        Path path = Paths.get(testFilePath);
        long expectedLineCount = Files.lines(path).count();

        // Run the wc tool with -l option
        WcTool.main(new String[]{"-l", testFilePath});

        // Check the output
        String output = outContent.toString().trim();
        String expectedOutput = expectedLineCount + " " + testFilePath;
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testWordCount() throws Exception {
        // Get the actual word count of the file
        Path path = Paths.get(testFilePath);
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        long expectedWordCount = content.split("\\s+").length;

        // Run the wc tool with -w option
        WcTool.main(new String[]{"-w", testFilePath});

        // Check the output
        String output = outContent.toString().trim();
        String expectedOutput = expectedWordCount + " " + testFilePath;
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testCharCount() throws Exception {
        // Get the actual character count of the file
        Path path = Paths.get(testFilePath);
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        long expectedCharCount = content.length();

        // Run the wc tool with -m option
        WcTool.main(new String[]{"-m", testFilePath});

        // Check the output
        String output = outContent.toString().trim();
        String expectedOutput = expectedCharCount + " " + testFilePath;
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testDefaultBehavior() throws Exception {
        // Get the actual counts of the file
        Path path = Paths.get(testFilePath);
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        long expectedLineCount = Files.lines(path).count();
        long expectedWordCount = content.split("\\s+").length;
        long expectedByteCount = Files.readAllBytes(path).length;

        // Run the wc tool with no options
        WcTool.main(new String[]{testFilePath});

        // Check the output
        String output = outContent.toString().trim();
        String expectedOutput = expectedLineCount + " " + expectedWordCount + " " + expectedByteCount + " " + testFilePath;
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testStdinWithLineCountOption() throws Exception {
        // Get the actual line count of the file
        Path path = Paths.get(testFilePath);
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        long expectedLineCount = Files.lines(path).count();

        // Set up System.in to read from the test file content
        ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
        System.setIn(in);

        // Run the wc tool with -l option and no filename
        WcTool.main(new String[]{"-l"});

        // Check the output
        String output = outContent.toString().trim();
        String expectedOutput = String.valueOf(expectedLineCount);
        assertEquals(expectedOutput, output);
    }
}
