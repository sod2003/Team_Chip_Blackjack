package com.skillstorm;

import java.util.Scanner;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    static void mainMenu() {
        boolean gameComplete = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Team Chip's Blackjack Game!\n");
        while(!gameComplete) {
            System.out.println("1. Login\n"
            + "2. Play a game of Blackjack as a Guest\n"
            + "3. See Leaderboard\n"
            + "4. Exit Game\n");
            String selection = sc.nextLine();
            process(selection);
        }
        System.out.println("Thanks for playing Team Chip's Blackjack Game! Logging you out now...");
        sc.close();
    }

    private static void process(String selection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'process'");
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        mainMenu();
    }
}
