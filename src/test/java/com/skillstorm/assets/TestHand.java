package com.skillstorm.assets;

import com.skillstorm.logic.GameLogic;

public class TestHand {

    public static void main(String[] args) {
        // Testing Hand
        // Deck deck = new Deck(Card.generateCards());
        // deck.shuffle();
        // Hand hand = new Hand();
        // hand.hit(deck);
        // hand.hit(deck);
        // hand.hit(deck);

        GameLogic gl = new GameLogic();
        Player player = new Player("Josh");
        gl.addPlayer(player);
        gl.getDeck().shuffle();
        gl.deal();
        player.getHand().hit(gl.getDeck());
        player.getHand().hit(gl.getDeck());
        player.getHand().hit(gl.getDeck());
        System.out.println(player.getHand().show());
        System.out.println(player.getHand().mask());

    }

}
