package com.example.ccwc;

public class FormatTest {
    public static void main(String[] args) {
        int value = 137;
        String formatted = String.format("%8d", value);
        System.out.println("Formatted: '" + formatted + "'");
        System.out.println("Length: " + formatted.length());
    }
}