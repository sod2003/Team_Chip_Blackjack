package com.skillstorm.logic;

import java.util.Scanner;

public class UI {

    public static Scanner scanner = new Scanner(System.in);

    /**
     * Method to get user input as int for multiple choice prompts.
     * Anything less than zero or greater than the amount will re-prompt the user.
     * 
     * @param prompt      string to ask the user for input
     * @param userChoices number of choices that are available
     * @return USER INPUT
     */
    public static int readInt(String prompt, int userChoices) {
        int input;

        do {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter one of the available numbers!");
                input = -1;
            }
        } while (input < 1 || input > userChoices);
        clearConsole();
        return input;
    }

    /**
     * Method to get user input as string. Use the prompt argument to ask the user
     * for input.
     * 
     * @param prompt to ask the user for input
     * @return USER INPUT
     */
    public static String readStr(String prompt) {
        String input;

        do {
            try {
                System.out.print(prompt);
                input = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter at least one valid character as your name.");
                input = "";
            }
        } while (input.length() < 1);
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
     * Prints a heading from parameter heading with separators above and below.
     * 
     * @param heading string that you want to print
     */
    public static void printHeading(String heading) {
        printSeparator(50);
        System.out.println(heading);
        printSeparator(50);
    }

    /**
     * Waits for the user to press enter. Gives them a chance to
     * read what is on the screen if needed.
     */
    public static void pressEnter() {
        System.out.print("\n<<<Press \"ENTER\" to continue>>>");
        scanner.nextLine();
        UI.clearConsole();
    }

}
