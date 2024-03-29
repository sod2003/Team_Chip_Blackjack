package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import com.skillstorm.assets.Card;
import com.skillstorm.assets.Deck;
import com.skillstorm.assets.Hand;
import com.skillstorm.assets.House;
import com.skillstorm.assets.Player;
import com.skillstorm.assets.Rank;

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
     * @param playerToAdd Player to be added to the current game
     */
    public void addActivePlayer(Player playerToAdd) {
        playerList.add(playerToAdd);
    }

    /**
     * Add a player to GameLogic's leaderboardList
     * This is what will be serialized and saved to a JSON file
     * 
     * @param playerToAdd player to be saved in the leaderboard
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

    public ArrayList<Player> getLeaderboardList() {
        return leaderboardList;
    }

    public void setLeaderboardList(ArrayList<Player> leaderboardList) {
        this.leaderboardList = leaderboardList;
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

    /**
     * Creates a new deck of cards for the current game and shuffles it
     */
    private void shuffleDeck() {
        deck = new Deck();
        deck.shuffle();
    }

    /**
     * Logic for accepting the player's bet
     * Includes the possibility of the player being out of earnings, and gives them
     * more to be able to continue playing
     */
    protected void takeBets() {
        for (Player player : playerList) {
            System.out.print("");
            double bet = 0.0;
            double loan = 100.00;
            // give player a "loan" if they dont have any earnings left, just to keep the
            // game going
            if (player.getEarnings() <= 0.0) {
                player.setEarnings(loan);
                UI.printHeading(String.format(
                        "%s, we noticed that you're out of money. We have generously granted you a loan of $%.2f...with a 40%% interest rate.",
                        player.getName(), loan));
            }
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
            player.getHand(0).setBet(bet);
            UI.printHeading(String.format("%s places a bet of $%.2f", player.getName(), bet));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Takes the player's name and bet and begins a new game
     */
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
            if (Rules.checkInsurance(house.getHand())) {
                insuranceBets();
            }
            if (!containsNaturals()) {
                for (Player player : playerList) {
                    for (int i = 0; i < player.getAllHands().size(); i++) {
                        Hand hand = player.getHand(i);
                        handlePlayerTurn(player, hand);
                    }
                }
                houseActions();
            }
            settlement();
            gameOver = !playAgain();

            // save current player list, exit game logic
            Save.save(playerList, leaderboardList);
        }
    }

    /**
     * Logic for taking insurance bet if the house is showing an Ace
     */
    private void insuranceBets() {
        for (Player player : playerList) {
            boolean betLogic = true;
            if (player.getEarnings() - player.getHand(0).getBet() > 0) {
                while (betLogic) {
                    double availableFunds = player.getHand(0).getBet() / 2.0;
                    String betCeilingStr = String.format("%.2f%n", availableFunds);
                    String answer = UI.readStr("Dealer is showing an Ace. Want to take an insurance bet of up to $"
                            + betCeilingStr + "? (Y or N)");
                    switch (answer.toUpperCase().charAt(0)) {
                        case 'Y':
                            boolean takeBetFlag = true;
                            while (takeBetFlag) {
                                String betString = UI.readStr("Enter a number between 0.0 and " + betCeilingStr);
                                if ((Double.valueOf(betString) instanceof Double) && Double.parseDouble(betString) > 0.0
                                        && Double.parseDouble(betString) <= availableFunds) {
                                    player.setInsurance(Double.parseDouble(betString));
                                    takeBetFlag = false;
                                }
                            }
                        case 'N':
                            betLogic = false;
                            break;
                    }
                }
            }
        }
    }

    /**
     * Logic for settling win/loss at the end of each round
     */
    private void settlement() {
        int houseHand = house.getHand().total();
        if (houseHand > 21) {
            UI.printHeading("House Busts! All remaining bets are winners.");
            for (Player player : playerList) {
                // House busts. All players, besides those that bust during player turn, win
                // bets.
                for (Hand hand : player.getAllHands()) {
                    if (hand.getBet() == 0.0) {
                        continue;
                    }
                    double winnings = hand.getBet();
                    player.increaseEarnings(winnings);
                    house.setEarnings(house.getEarnings() - winnings);
                }
            }
        } else {
            for (Player player : playerList) {
                for (int i = 0; i < player.getAllHands().size(); i++) {
                    Hand hand = player.getHand(i);
                    if (houseHand > hand.total()) {
                        house.setEarnings(house.getEarnings() + hand.getBet()); // House earns player bet.
                        player.decreaseEarnings(hand.getBet());
                        UI.printHeading(String.format("The house beat %s and collected their bet.", player.getName()));
                    } else if (houseHand == hand.total()) {
                        UI.printHeading(
                                String.format("Game ends in a DRAW between %s and the house!", player.getName()));
                    } else {
                        if (hand.total() <= 21) {
                            // Player beats house. Wins bet.
                            double winnings = hand.getBet();
                            player.increaseEarnings(winnings);
                            house.setEarnings(house.getEarnings() - winnings);
                            UI.printHeading(String.format("%s WINS %.2f!!!", player.getName(), (winnings)));
                        }
                    }
                }
            }
        }
        house.getHand().clear();
        for (Player player : playerList) {
            player.dropHands();
            player.setFirstHand(true);
        }
        UI.pressEnter();
    }

    /**
     * AI logic for the house's turn
     */
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

    /**
     * 
     * Logic for handling the different options during a player's turn
     * 
     * @param player current player
     * @param hand   current player's hand
     */
    protected void handlePlayerTurn(Player player, Hand hand) {
        boolean endTurn = false;

        while (!endTurn) {
            if (hand.total() > 21) {
                UI.printHeading("BUST! " + player.getName() + " has " + hand.total() + ".");
                double loss = hand.getBet();
                player.decreaseEarnings(loss);
                hand.setBet(0); // Zeroing out the bet.
                house.setEarnings(house.getEarnings() + loss);
                endTurn = true;
            } else if (hand.total() == 21) {
                UI.printHeading("BLACKJACK! " + player.getName() + " has " + hand.total() + ".");
                endTurn = true;
            } else {
                if (endTurn)
                    break;
                showTable(player, hand);
                int playerAction = UI.readInt(printOptions(player, hand), 4);
                switch (playerAction) {
                    case 1:
                        hand.hit(deck.draw());
                        break;
                    case 2:
                        endTurn = true;
                        break;
                    case 3:
                        if (Rules.checkSplit(player, hand)) {
                            player.addNewHand(hand.split());
                        }
                    case 4:
                        if (Rules.checkDouble(hand) && player.getEarnings() > 0 && player.firstHand()) {
                            if (player.getEarnings() - hand.getBet() < hand.getBet()) {
                                UI.printHeading("Sorry, you don't have enough funds to double your bet.");
                                UI.pressEnter();
                            } else {
                                hand.setBet(hand.getBet() * 2.0);
                                UI.printHeading(
                                        String.format("%s's bet doubled to %.2f.",
                                                player.getName(), hand.getBet()));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                hand.hit(deck.draw()); // Allowed one card for doubling down
                                showTable(player, hand);
                                UI.pressEnter();
                                endTurn = true;
                                continue; // Guarantees a check for Bust before settlement
                            }
                        }
                }
            }
        }
        player.setFirstHand(false);
    }

    /**
     * Logic for checking if the house has naturals in their hand
     * 
     * @return true or false
     */
    private boolean containsNaturals() {
        if (house.getHand().total() == 21) {
            UI.printHeading("House has a natural!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (house.getHand().getCardList().getFirst().getRank() == Rank.ACE) {
                UI.printHeading("House pays out insurance bets to players.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Player player : playerList) {
                    house.setEarnings(house.getEarnings() - player.getInsurance());
                    player.winInsurance();
                }
            }
            return true;
        } else {
            for (Player player : playerList) {
                if (house.getHand().getCardList().getFirst().getRank() == Rank.ACE) {
                    if (player.getInsurance() > 0.0) {
                        UI.printHeading(String.format("%s loses insurance bet of %.2f", player.getName(),
                                player.getInsurance()));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        house.setEarnings(house.getEarnings() + player.getInsurance());
                        player.loseInsurance();
                    }
                }
                for (Hand hand : player.getAllHands()) {
                    if (hand.total() == 21) {
                        UI.printHeading(String.format("%s has a natural hand of 21!", player.getName()));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        hand.setBet(hand.getBet() * 1.5);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Options to display during the player's turn
     * Conditional depending on the contents of the player's hand
     * 
     * @param player current player
     * @param hand   current player's hand
     * @return
     */
    private String printOptions(Player player, Hand hand) {
        String str = "1. Hit\n2. Stay\n";
        if (Rules.checkSplit(player, hand))
            str += "3. Split\n";
        if (player.firstHand() && Rules.checkDouble(hand))
            str += "4. Double\n";
        return str;
    }

    /**
     * Displays both the player's and house's hands in ASCII form on the screen
     * 
     * @param player
     * @param hand
     */
    private void showTable(Player player, Hand hand) {
        UI.clearConsole();
        System.out.println("The House Hand: \n" + house.getHand().mask());
        System.out.println(
                player.getName() + "'s Hand with " + hand.total() + ":\n" + hand.show());
    }

    /**
     * Displays the leaderboard for the player
     */
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

    /**
     * Logic for asking if the player wants to play again
     * 
     * @return boolean based on player's choice
     */
    private boolean playAgain() {
        while (true) {
            int choice = UI.readInt("Want to play another round?\n1. Yes\n2. No\n", 2);
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

}
