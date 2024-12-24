package ru.nsu.bondar;

import static ru.nsu.bondar.SubstringFinder.find;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main-Класс, содержащий entry point.
 */
public class Main {
    static List<Integer> result;

    /**
     * Entry point программы.
     *
     * @param args аргументы программы
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter filename: ");
            String filename = scanner.nextLine();

            System.out.print("Enter pattern: ");
            String pattern = scanner.nextLine();

            result = find(filename, pattern);
            System.out.println("Pattern found at positions: " + result);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}