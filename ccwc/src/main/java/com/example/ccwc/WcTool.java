package com.example.ccwc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

/**
 * WcTool - A Java implementation of the Unix 'wc' command line tool.
 * This tool counts bytes, lines, words, and characters in files.
 */
public class WcTool {

    public static void main(String[] args) {
        if (args.length == 0) {
            // No arguments provided, read from stdin
            processStdin();
        } else {
            processArgs(args);
        }
    }

    private static void processArgs(String[] args) {
        boolean countBytes = false;
        boolean countLines = false;
        boolean countWords = false;
        boolean countChars = false;
        String filePath = null;

        // Parse command line arguments
        for (String arg : args) {
            if (arg.startsWith("-")) {
                // This is an option
                for (int i = 1; i < arg.length(); i++) {
                    char option = arg.charAt(i);
                    switch (option) {
                        case 'c':
                            countBytes = true;
                            break;
                        case 'l':
                            countLines = true;
                            break;
                        case 'w':
                            countWords = true;
                            break;
                        case 'm':
                            countChars = true;
                            break;
                        default:
                            System.err.println("Unknown option: " + option);
                            printUsage();
                            System.exit(1);
                    }
                }
            } else {
                // This is a file path
                filePath = arg;
            }
        }

        // If no options are specified, show all counts
        if (!countBytes && !countLines && !countWords && !countChars) {
            countBytes = true;
            countLines = true;
            countWords = true;
        }

        if (filePath == null) {
            // No file path provided, read from stdin
            processStdin(countBytes, countLines, countWords, countChars);
        } else {
            // Process the file
            processFile(filePath, countBytes, countLines, countWords, countChars);
        }
    }

    private static void processStdin() {
        processStdin(true, true, true, false);
    }

    private static void processStdin(boolean countBytes, boolean countLines, boolean countWords, boolean countChars) {
        try {
            // Read all input from stdin
            StringBuilder content = new StringBuilder();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            String input = content.toString();
            printCounts(input, countBytes, countLines, countWords, countChars, "");
        } catch (Exception e) {
            System.err.println("Error processing stdin: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void processFile(String filePath, boolean countBytes, boolean countLines, boolean countWords, boolean countChars) {
        try {
            Path path = Paths.get(filePath);
            File file = path.toFile();

            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                System.exit(1);
            }

            if (countBytes || countLines || countWords || countChars) {
                String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                printCounts(content, countBytes, countLines, countWords, countChars, filePath);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printCounts(String content, boolean countBytes, boolean countLines, boolean countWords, boolean countChars, String filePath) {
        StringBuilder result = new StringBuilder();

        if (countLines) {
            long lineCount = content.split("\n", -1).length - (content.endsWith("\n") ? 1 : 0);
            result.append(lineCount);
        }

        if (countWords) {
            long wordCount = Arrays.stream(content.split("\\s+"))
                    .filter(s -> !s.isEmpty())
                    .count();
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(wordCount);
        }

        if (countChars) {
            long charCount = content.length();
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(charCount);
        }

        if (countBytes) {
            long byteCount = content.getBytes(StandardCharsets.UTF_8).length;
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(byteCount);
        }

        if (!filePath.isEmpty()) {
            result.append(" ").append(filePath);
        }
        String output = result.toString();
        System.out.println(output);
    }

    private static void printUsage() {
        System.out.println("Usage: ccwc [OPTION]... [FILE]");
        System.out.println("Print newline, word, and byte counts for FILE.");
        System.out.println("With no FILE, read standard input.");
        System.out.println();
        System.out.println("  -c    print the byte counts");
        System.out.println("  -l    print the newline counts");
        System.out.println("  -w    print the word counts");
        System.out.println("  -m    print the character counts");
        System.out.println();
        System.out.println("If no options are specified, print line, word, and byte counts.");
    }
}
