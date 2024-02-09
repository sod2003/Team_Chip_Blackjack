package com.skillstorm.assets;

public class TestDeck {
    public static void main(String[] args) {
        Deck deck = new Deck(Card.generateCards());
        System.out.println(deck);
        deck.shuffle();
        System.out.println(deck);

        for (Card card : deck.getCards()) {
            System.out.println(card.ascii());
            card.setFaceUp(true);
            System.out.println(card.ascii());

        }

    }
}
