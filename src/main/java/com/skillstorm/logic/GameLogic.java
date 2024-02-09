package com.skillstorm.logic;

import java.util.ArrayList;
import com.skillstorm.assets.Deck;
import com.skillstorm.assets.House;
import com.skillstorm.assets.Player;

/*
 * A class for managing the overall logic of the game.
 * The properties and methods within could potentially move to different classes if deemed appropriate.
 */

public class GameLogic {

    private ArrayList<Player> playerList = new ArrayList<>();
    private House house;
    private Deck deck;

    /**
     * Logic for dealing cards to everyone in the game, including the house.
     * Includes possibility of having multiple players.
     */
    public void deal() {
        // Deal each player a card and set it to be face up
        for (Player player : playerList) {
            player.getHand().hit(deck);
            player.getHand().getCardList().getFirst().setFaceUp(true);
        }
        // Deal house a card and set it to face up
        house.getHand().hit(deck);
        house.getHand().getCardList().getFirst().setFaceUp(true);
        // Deal each player a second card but leave it face down
        for (Player player : playerList) {
            player.getHand().hit(deck);
        }
        // Deal second card to house but leave it face down
        house.getHand().hit(deck);
    }

    private void shuffleDeck() {
        deck = new Deck();
        deck.shuffle();
    }

    private void takeBets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeBets'");
    }

    public void startGame() {
        boolean gameOver = false;

        while(!gameOver) {
            takeBets();
            shuffleDeck();
            deal();
            for (Player player : playerList) {
                handlePlayerTurn(player);
            }
        }
    }

    private void handlePlayerTurn(Player player) {
        boolean endTurn = false;

        while (!endTurn) {
            if (player.getHand().total() > 21) {
                System.out.println("BUST! " + player.getName() + " has " + player.getHand().total() + ".");
                // TODO Lose bet here
                endTurn = true;
            } else if (player.getHand().total() == 21) {
                System.out.println("JACKPOT! " + player.getName() + " has " + player.getHand().total() + ".");
                // TODO Win bet here
            }
            showTable();
            printOptions();
        }
    }

    private void printOptions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printOptions'");
    }

    private void showTable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showTable'");
    }

    public void printLeaderboard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printLeaderboard'");
    }
}
