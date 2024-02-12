package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import com.skillstorm.assets.Card;
import com.skillstorm.assets.Deck;
import com.skillstorm.assets.Hand;
import com.skillstorm.assets.House;
import com.skillstorm.assets.Player;

/*
 * A class for managing the overall logic of the game.
 * The properties and methods within could potentially move to different classes if deemed appropriate.
 */

public class GameLogic {

    private ArrayList<Player> playerList = new ArrayList<>();
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
        player.getHand(0).setBet(bet);
    }

    /**
     * Logic for dealing cards to everyone in the game, including the house.
     * Includes possibility of having multiple players.
     */
    public void deal() {
        // Deal each player a card and set it to be face up
        for (Player player : playerList) {
            player.getHand(0).hit(deck.draw());
            player.getHand(0).getCardList().getFirst().setFaceUp(true);
        }
        // Deal house a card and set it to face up
        house.getHand().hit(deck.draw());
        house.getHand().getCardList().getFirst().setFaceUp(true);
        // Deal each player a second card but leave it face down
        for (Player player : playerList) {
            player.getHand(0).hit(deck.draw());
        }
        // Deal second card to house but leave it face down
        house.getHand().hit(deck.draw());
        UI.printHeading("Dealing...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                String reponse = UI
                        .readStr(String.format("%s, you currently have $%.2f; Place your bet: ", player.getName(),
                                player.getEarnings()));
                try {
                    bet = Double.parseDouble(reponse);
                } catch (NumberFormatException e) {
                    System.out.println("That's not a valid number.");
                    UI.pressEnter();
                    continue;
                }
            }
            player.addNewHand();
            player.getHand(0).setBet(bet);
            UI.printHeading(String.format("%s places a bet of $%.2f", player.getName(), bet));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startGame() {
        playerList.clear();
        boolean gameOver = false;

        // Load JSON save file to populate leaderboardList
        this.leaderboardList = Load.load();
        // Take name input
        String playerName = UI.readStr("Please enter your name: ");
        try {
            // If player with the same name is in the leaderboardList, add them to the
            // active playerList
            // TODO here is where we are getting a duplicate reference to the player in
            // playerList if they back out to the main menu and then re-enter the game.
            // Currently clearing the playerList each game to solve this.
            playerList.add(Load.getReturningPlayer(playerName, leaderboardList));
            UI.printHeading(String.format(
                    "Welcome back, %s! Ready for some more BlackJack?",
                    playerName));
            UI.pressEnter();
        } catch (NoSuchElementException e) {
            // If retrieving an existing player fails(exception thrown), create a new one
            // with their name
            playerList.add(new Player(playerName));
            UI.printHeading(String.format(
                    "Welcome, %s! We're happy to have you here at Team Chip's Casino. Let's play some BlackJack!",
                    playerName));
            UI.pressEnter();
        }

        while (!gameOver) {
            takeBets();
            shuffleDeck();
            deal();
            if (!containsNaturals()) {
                for (Player player : playerList) {
                    for (Hand hand : player.getAllHands()) {
                        // TODO Spliting / Doubling Down / Insurance
                        handlePlayerTurn(player, hand);
                    }
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
                for (Hand hand : player.getAllHands()) {
                    double winnings = hand.getBet();
                    player.increaseEarnings(winnings);
                    house.setEarnings(house.getEarnings() - winnings);
                }
            }
        } else {
            for (Player player : playerList) {
                for (Hand hand : player.getAllHands()) {
                    if (houseHand > hand.total()) {
                        house.setEarnings(house.getEarnings() + hand.getBet()); // House earns player bet.
                        UI.printHeading(String.format("The house beat %s and collected their bet.", player.getName()));
                    } else if (houseHand == hand.total()) {
                        UI.printHeading(String.format("Game ends in a DRAW between %s and the house!", player.getName()));
                    } else {
                        // Player beats house. Wins bet.
                        double winnings = hand.getBet();
                        player.increaseEarnings(winnings);
                        house.setEarnings(house.getEarnings() - winnings);
                        UI.printHeading(String.format("%s WINS %.2f!!!", player.getName(), (winnings)));
                    }
                }
            }
        }
        house.getHand().clear();
        for (Player player : playerList) {
            player.dropHands();
        }
        UI.pressEnter();
    }

    private void houseActions() {
        UI.printHeading("It is now the house's turn.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (house.getHand().total() < 17) {
            UI.clearConsole();
            UI.printHeading("The house hits.");
            house.getHand().hit(deck.draw());
            for (Player player : playerList) {
                showTable(player, player.getHand(0));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (house.getHand().total() >= 17 && house.getHand().total() <= 21) {
            UI.printHeading(String.format("The house stays with %d.", house.getHand().total()));
        }
    }

    protected void handlePlayerTurn(Player player, Hand hand) {
        boolean endTurn = false;

        while (!endTurn) {
            if (hand.total() > 21) {
                System.out.println("BUST! " + player.getName() + " has " + hand.total() + ".");
                double loss = hand.getBet();
                house.setEarnings(house.getEarnings() + loss);
                endTurn = true;
            } else if (hand.total() == 21) {
                System.out.println("BLACKJACK! " + player.getName() + " has " + hand.total() + ".");
                endTurn = true;
            } else {
                showTable(player, hand);
                int playerAction = UI.readInt(printOptions(), 2);
                switch (playerAction) {
                    case 1:
                        hand.hit(deck.draw());
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
            System.out.println("House has a natural!");
            return true;
        } else {
            for (Player player : playerList) {
                for (Hand hand : player.getAllHands()) {
                    if (hand.total() == 21) {
                        System.out.println(player.getName() + " has a natural!");
                        hand.setBet(hand.getBet() * 1.25);
                    }
                }
            }
        }
        return false;
    }

    private String printOptions() {
        return "1. Hit\n2. Stay\n";
    }

    private void showTable(Player player, Hand hand) {
        UI.clearConsole();
        System.out.println("The House Hand: \n" + house.getHand().mask());
        System.out.println(
                player.getName() + "'s Hand with " + hand.total() + ":\n" + hand.show());
    }

    public void printLeaderboard() {
        leaderboardList = Load.load();
        if (leaderboardList == null || leaderboardList.isEmpty()) {
            System.out.println("The leaderboard is empty.");
            return;
        }

        int rank = 1;
        final int leaderboardMaxSize = 10;

        // sort the list of players in descending order based on their earnings
        Collections.sort(leaderboardList, (o1, o2) -> new PlayerEarningsComparator().compare(o1,
                o2));
        Collections.reverse(leaderboardList);

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
        UI.pressEnter();
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
                    UI.pressEnter();
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
