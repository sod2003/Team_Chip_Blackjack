package com.skillstorm.assets;

public class TestHand {

    public static void main(String[] args) {
        // Testing Hand
        Deck deck = new Deck(Card.generateCards());
        deck.shuffle();
        Hand hand = new Hand();
        hand.hit(deck);
        hand.hit(deck);
        System.out.println(hand.total());
    }

}
