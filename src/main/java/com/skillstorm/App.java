package com.skillstorm;

import com.skillstorm.logic.GameLogic;
import com.skillstorm.logic.UI;

/**
 * Hello world!
 */
public final class App {
    static GameLogic gl = new GameLogic();

    private App() {
    }

    static void mainMenu() {
        boolean gameComplete = false;

        UI.clearConsole(); // Wipes any previous text from the console at the beginning of the game.
        while (!gameComplete) {
            UI.printHeading("Welcome to Team Chip's Blackjack Game!");
            int selection = UI.readInt("1. Start Game\n"
                    + "2. See Leaderboard\n"
                    + "3. Exit Game\n", 3);
            switch (selection) {
                case 1:
                    gl.startGame();
                    break;
                case 2:
                    gl.printLeaderboard();
                    break;
                case 3:
                    gameComplete = true;
                    break;
                default:
                    System.out.println("Sorry, I don't recognize that input. Try again.");
            }
        }
        UI.printHeading("Thanks for playing Team Chip's Blackjack Game! Logging you out now...");
    }

    /**
     * Play some BlackJack.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        mainMenu();
    }
}
