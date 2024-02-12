package com.skillstorm.logic;

import java.util.Scanner;

public class UI {

    public static Scanner scanner = new Scanner(System.in);

    /**
     * Method to get user input as int for multiple choice prompts.
     * Use the prompt argument to ask the user for input.
     * Use the userChoices argument to set the amount of choices they have
     * available.
     * Anything less than zero or greater than the amount will re-prompt the user.
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
     * @param prompt
     * @param userChoices
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
    public static void pressEnter() {
        System.out.print("\n<<<Press \"ENTER\" to continue>>>");
        scanner.nextLine();
        UI.clearConsole();
    }

}
