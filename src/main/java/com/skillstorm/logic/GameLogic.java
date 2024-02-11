package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skillstorm.assets.Card;
import com.skillstorm.assets.Deck;
import com.skillstorm.assets.House;
import com.skillstorm.assets.Player;

/*
 * A class for managing the overall logic of the game.
 * The properties and methods within could potentially move to different classes if deemed appropriate.
 */

public class GameLogic {

    private ArrayList<Player> playerList = new ArrayList<>();
    private Map<String, Double> bets = new HashMap<String, Double>();
    private House house = new House();
    private Deck deck = new Deck(Card.generateCards());

    /**
     * Add a player to GameLogic's playerList
     * 
     * @param playerToAdd
     */
    public void addPlayer(Player playerToAdd) {
        playerList.add(playerToAdd);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void addBet(Player player, Double bet) {
        bets.put(player.getName(), bet);
        player.decreaseEarnings(bet);
    }

    /**
     * Logic for dealing cards to everyone in the game, including the house.
     * Includes possibility of having multiple players.
     */
    public void deal() {
        // Deal each player a card and set it to be face up
        for (Player player : playerList) {
            player.getHand().hit(deck.draw());
            player.getHand().getCardList().getFirst().setFaceUp(true);
        }
        // Deal house a card and set it to face up
        house.getHand().hit(deck.draw());
        house.getHand().getCardList().getFirst().setFaceUp(true);
        // Deal each player a second card but leave it face down
        for (Player player : playerList) {
            player.getHand().hit(deck.draw());
        }
        // Deal second card to house but leave it face down
        house.getHand().hit(deck.draw());
    }

    private void shuffleDeck() {
        deck = new Deck();
        deck.shuffle();
    }

    protected void takeBets() {
        for (Player player : playerList) {
            System.out.print("");
            double bet = 0.0;
            while ((bet <= 0.0) || (bet > player.getEarnings())) {
                String reponse = UI.readStr("Place your bets: ");
                try {
                    bet = Double.parseDouble(reponse);
                } catch (NumberFormatException e) {
                    System.out.println("That's not a valid number.");
                    UI.pressAnyKey();
                    continue;
                }
            }
            bets.put(player.getName(), bet);
            player.decreaseEarnings(bet);
            System.out.println(player.getName() + " places a bet of $" + bet);
        }
    }

    public void startGame() {
        boolean gameOver = false;

        // TODO Load method for leaderboard, take name input, create Player

        while(!gameOver) {
            takeBets();
            shuffleDeck();
            deal();
            // TODO Naturals rule needs implementing here
            for (Player player : playerList) {
                // TODO Spliting / Doubling Down / Insurance
                handlePlayerTurn(player);
            }
            // TODO Handle House, Settlement
        }
        // TODO save current player list, exit game logic
    }

    protected void handlePlayerTurn(Player player) {
        boolean endTurn = false;

        while (!endTurn) {
            if (player.getHand().total() > 21) {
                System.out.println("BUST! " + player.getName() + " has " + player.getHand().total() + ".");
                double loss = bets.remove(player.getName());
                house.setEarnings(house.getEarnings() + loss);
                endTurn = true;
            } else if (player.getHand().total() == 21) {
                System.out.println("JACKPOT! " + player.getName() + " has " + player.getHand().total() + ".");
                double winnings = bets.remove(player.getName());
                player.increaseEarnings(winnings * 2);
                house.setEarnings(house.getEarnings() - winnings);
                endTurn = true;
            } else {
                showTable(player);
                int playerAction = UI.readInt(printOptions(), 2);
                switch (playerAction) {
                    case 1:
                        player.getHand().hit(deck.draw());
                        break;
                    case 2:
                        endTurn = true;
                        break;
                    case 3:
                        // TODO Split?
                }
            }
        }
    }

    private String printOptions() {
        return "1. Hit\n2. Stay\n";
    }

    private void showTable(Player player) {
        // UI.clearConsole();
        System.out.println("The House Hand:\n" + house.getHand().mask());
        System.out.println("Your Hand with " + player.getHand().total() + ":\n" + player.getHand().show());
    }

    public void printLeaderboard(List<Player> playerList) {
        if (playerList == null || playerList.isEmpty()) {
            System.out.println("The leaderboard is empty.");
            return;
        }

        int rank = 1;
        final int leaderboardMaxSize = 10;

        // sort the list of players in descending order based on their earnings
        System.out.println("before sort" + playerList);
        Collections.sort(playerList, (o1, o2) -> new PlayerComparator().compare(o1,
                o2));
        Collections.reverse(playerList);
        System.out.println("after sort" + playerList);

        // print the sorted list to the screen
        System.out.println("###############################");
        System.out.println("######### LEADERBOARD #########");
        System.out.println("###############################");
        for (Player plyr : playerList) {
            System.out.printf("%d: %s total earnings: $%.2f%n", rank, plyr.getName(), plyr.getEarnings());
            rank++;
            if (rank >= leaderboardMaxSize)
                break;
        }
    }
}
