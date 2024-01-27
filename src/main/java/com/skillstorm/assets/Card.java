package com.skillstorm.assets;

// The Card class, not specifically mentioned in the requirements, but it's a good design decision to delegate.
public class Card {
    private Suit suit;
    private int rank;

    private Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public static Card[] generateCards() {
        Card[] cards = new Card[52];
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (int i = 1; i <= 13; i++, index++) {
                cards[index] = new Card(suit, i);
            }
        }
        return cards;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        result = prime * result + rank;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (suit != other.suit)
            return false;
        if (rank != other.rank)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
