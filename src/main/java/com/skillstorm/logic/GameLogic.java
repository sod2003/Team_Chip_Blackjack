package com.skillstorm.logic;

import java.util.LinkedList;
import com.skillstorm.assets.Deck;
import com.skillstorm.assets.House;
import com.skillstorm.assets.Player;

/*
 * A class for managing the overall logic of the game.
 * The properties and methods within could potentially move to different classes if deemed appropriate.
 */

public class GameLogic {

    private LinkedList<Player> playerList;
    private House house;
    private Deck deck;
    // private List<Player> playerData = Load.loadPlayerData(null);

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

}
