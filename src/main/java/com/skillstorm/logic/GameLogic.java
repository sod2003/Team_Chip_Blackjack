package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void startGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    public void printLeaderboard(List<Player> playerList) {
        int rank = 1;
        final int leaderboardMaxSize = 10;
        // sort the list of players in descending order based on their earnings
        System.out.println("before sort" + playerList);
        Collections.sort(playerList, Collections.reverseOrder());
        // Collections.sort(playerList, (o1, o2) -> new PlayerComparator().compare(o1,
        // o2));
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
