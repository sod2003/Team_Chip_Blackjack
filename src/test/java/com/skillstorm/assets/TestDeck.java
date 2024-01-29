package com.skillstorm.assets;

public class TestDeck {
    public static void main(String[] args) {
        Deck deck = new Deck(Card.generateCards());
        System.out.println(deck);
        deck.shuffle();
        System.out.println(deck);
    }
}
