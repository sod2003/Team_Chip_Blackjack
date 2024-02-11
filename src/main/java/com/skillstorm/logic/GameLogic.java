package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private Map<String, Double> bets = new HashMap<String, Double>();
    private ArrayList<Player> leaderboardList = new ArrayList<>();
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

        // Load JSON save file to populate leaderboardList
        this.leaderboardList = Load.load();
        // Take name input
        String playerName = UI.readStr("Please enter your name: ");
        try {
            // If player with the same name is in the leaderboardList, add them to the
            // active playerList
            playerList.add(Load.getReturningPlayer(playerName, leaderboardList));
            UI.printHeading(String.format(
                    "Welcome back, %s! Ready for some more BlackJack?",
                    playerName));
            UI.pressAnyKey();
        } catch (NoSuchElementException e) {
            // If retrieving an existing player fails(exception thrown), create a new one
            // with their name
            playerList.add(new Player(playerName));
            UI.printHeading(String.format(
                    "Welcome, %s! We're happy to have you here at Team Chip's Casino. Let's play some BlackJack!",
                    playerName));
            UI.pressAnyKey();
        }

        while (!gameOver) {
            takeBets();
            shuffleDeck();
            deal();
            if (!containsNaturals()) {
                for (Player player : playerList) {
                    // TODO Spliting / Doubling Down / Insurance
                    handlePlayerTurn(player);
                }
                houseActions();
            }
            settlement();
            gameOver = !playAgain();
        }
        // TODO save current player list, exit game logic
    }

    private void settlement() {
        int houseHand = house.getHand().total();
        if (houseHand > 21) {
            UI.printHeading("House Busts! All remaining bets are winners.");
            for (Player player : playerList) {
                // House busts. All players, besides those that bust during player turn, win
                // bets.
                if (bets.containsKey(player.getName())) {
                    double winnings = bets.remove(player.getName());
                    player.increaseEarnings(winnings * 2);
                    house.setEarnings(house.getEarnings() - winnings);
                }
            }
        } else {
            for (Player player : playerList) {
                if (!bets.containsKey(player.getName())) {
                    continue; // Bet was previously removed by Player bust.
                }

                if (houseHand > player.getHand().total()) {
                    house.setEarnings(house.getEarnings() + bets.remove(player.getName())); // House earns player bet.
                } else if (houseHand == player.getHand().total()) {
                    player.increaseEarnings(bets.remove(player.getName())); // House ties player. Bet returned to
                                                                            // player.
                } else {
                    // Player beats house. Wins bet.
                    double winnings = bets.remove(player.getName());
                    player.increaseEarnings(winnings * 2);
                    house.setEarnings(house.getEarnings() - winnings);
                }
            }
        }
        house.getHand().clear();
        for (Player player : playerList) {
            player.getHand().clear();
        }
    }

    private void houseActions() {
        while (house.getHand().total() < 17) {
            house.getHand().hit(deck.draw());
        }
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
                System.out.println("BLACKJACK! " + player.getName() + " has " + player.getHand().total() + ".");
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

    private boolean containsNaturals() {
        if (house.getHand().total() == 21) {
            return true;
        } else {
            for (Player player : playerList) {
                if (player.getHand().total() == 21) {
                    bets.put(player.getName(), bets.get(player.getName()) * 1.25);
                }
            }
        }
        return false;
    }

    private String printOptions() {
        return "1. Hit\n2. Stay\n";
    }

    private void showTable(Player player) {
        // UI.clearConsole();
        System.out.println("The House Hand:\n" + house.getHand().mask());
        System.out.println("Your Hand with " + player.getHand().total() + ":\n" + player.getHand().show());
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

    private boolean playAgain() {
        while (true) {
            int choice = UI.readInt("Want to play again?\n1. Yes\n2. No", 2);
            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("That's not an option. Try again.");
                    UI.pressAnyKey();
            }
        }
    }

    public ArrayList<Player> getLeaderboardList() {
        return leaderboardList;
    }

    public void setLeaderboardList(ArrayList<Player> leaderboardList) {
        this.leaderboardList = leaderboardList;
    }
}
