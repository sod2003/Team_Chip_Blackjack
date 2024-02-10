package com.skillstorm;

import java.util.Scanner;

import com.skillstorm.logic.GameLogic;
import com.skillstorm.logic.Load;

/**
 * Hello world!
 */
public final class App {
    static GameLogic gl = new GameLogic();
    private App() {
    }

    static void mainMenu() {
        boolean gameComplete = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Team Chip's Blackjack Game!\n");
        while(!gameComplete) {
            printMenu();
            String selection = sc.nextLine();
            switch(selection) {
                case "1":
                    gl.startGame();
                case "2":
                    gl.printLeaderboard();
                case "3":
                    gameComplete = true;
                default:
                    System.out.println("Sorry, I don't recognize that input. Try again.");
            }
        }
        System.out.println("Thanks for playing Team Chip's Blackjack Game! Logging you out now...");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("1. Start Game\n"
            + "2. See Leaderboard\n"
            + "3. Exit Game\n");
    }

    // private static void printAltMenu() {
    //     System.out.println("1. Login\n"
    //         + "2. Play a game of Blackjack as a Guest\n"
    //         + "3. See Leaderboard\n"
    //         + "4. Exit Game\n");
    // }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        mainMenu();
    }
}
