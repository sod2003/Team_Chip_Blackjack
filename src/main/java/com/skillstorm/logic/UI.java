package com.skillstorm.logic;

import java.util.Scanner;

public class UI {

    public static Scanner scanner = new Scanner(System.in);

    /**
     * Method to get user input
     * 
     * @param prompt
     * @param userChoices
     * @return USER INPUT
     */
    public static int readInt(String prompt, int userChoices) {
        int input;

        do {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                System.out.println("Please enter one of the available numbers!");
                input = -1;
            }
        } while (input < 1 || input > userChoices);
        clearConsole();
        return input;
    }

    /**
     * Method to clear the console. Prints several empty lines.
     */
    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    /**
     * Prints a separator line. Used for the printHeading method.
     */
    public static void printSeparator(int size) {
        for (int i = 0; i < size; i++)
            System.out.print("-");
        System.out.println();
    }

    /**
     * Prints a heading from parameter title with separators above and below.
     * 
     * @param title
     */
    public static void printHeading(String title) {
        printSeparator(30);
        System.out.println(title);
        printSeparator(30);
    }

    /**
     * Waits for the user to input any value and press enter. Gives them a chance to
     * read what is on the screen if needed.
     */
    public static void pressAnyKey() {
        System.out.print("\nEnter anything to continue>>>");
        scanner.next();
        UI.clearConsole();
    }

}
