package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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
    private ArrayList<Player> leaderboardList = new ArrayList<>();
    private Map<Player, Double> bets = new HashMap<Player, Double>();
    private House house = new House();
    private Deck deck = new Deck(Card.generateCards());

    /**
     * Add a player to GameLogic's playerList
     * 
     * @param playerToAdd
     */
    public void addActivePlayer(Player playerToAdd) {
        playerList.add(playerToAdd);
    }

    /**
     * Add a player to GameLogic's leaderboardList
     * This is what will be serialized and saved to a JSON file
     * 
     * @param playerToAdd
     */
    public void addLeaderboardPlayer(Player playerToAdd) {
        leaderboardList.add(playerToAdd);
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
            double bet = 0;
            while ((bet <= 0) || (bet > player.getEarnings())) {
                String reponse = UI.readStr("Place your bets: ");
                try {
                    bet = Double.parseDouble(reponse);
                } catch (NumberFormatException e) {
                    System.out.println("That's not a valid number.");
                    UI.pressAnyKey();
                    continue;
                }
            }
            bets.putIfAbsent(player, bet);
            player.decreaseEarnings(bet);
            System.out.println(player.getName() + " places a bet of $" + bet);
        }
    }

    public void startGame() {
        boolean gameOver = false;

        // Load JSON save file to populate leaderboardList
        this.leaderboardList = Load.load();
        // Take name input
        String playerName = UI.readStr("Please enter your name: ");
        try {
            // If player with the same name is in the leaderboardList, add them to the
            // active playerList
            playerList.add(Load.getReturningPlayer(playerName, leaderboardList));
        } catch (NoSuchElementException e) {
            // If retrieving an existing player fails(exception thrown), create a new one
            // with their name
            playerList.add(new Player(playerName));
        }

        while (!gameOver) {
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
            int playerAction = 0; // TODO Take input from player for action
            switch (playerAction) {
                case 1:
                    // TODO Hit Action
                case 2:
                    // TODO Stay Action
                case 3:
                    // TODO Split?
            }
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
        if (leaderboardList == null || leaderboardList.isEmpty()) {
            System.out.println("The leaderboard is empty.");
            return;
        }

        int rank = 1;
        final int leaderboardMaxSize = 10;

        // sort the list of players in descending order based on their earnings
        System.out.println("before sort" + leaderboardList);
        Collections.sort(leaderboardList, (o1, o2) -> new PlayerComparator().compare(o1,
                o2));
        Collections.reverse(leaderboardList);
        System.out.println("after sort" + leaderboardList);

        // print the sorted list to the screen
        System.out.println("###############################");
        System.out.println("######### LEADERBOARD #########");
        System.out.println("###############################");
        for (Player plyr : leaderboardList) {
            System.out.printf("%d: %s total earnings: $%.2f%n", rank, plyr.getName(), plyr.getEarnings());
            rank++;
            if (rank >= leaderboardMaxSize)
                break;
        }
    }

    public ArrayList<Player> getLeaderboardList() {
        return leaderboardList;
    }

    public void setLeaderboardList(ArrayList<Player> leaderboardList) {
        this.leaderboardList = leaderboardList;
    }
}
