package com.skillstorm.assets;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new LinkedList<>(Arrays.asList(Card.generateCards()));
    }

    public Deck(Card[] cards) {
        this.cards = new LinkedList<>(Arrays.asList(cards));
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * draw a single card from the deck
     * 
     * @return card
     */
    public Card draw() {
        return cards.remove(0);
    }

    /**
     * Shuffle the deck
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cards == null) ? 0 : cards.hashCode());
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
        Deck other = (Deck) obj;
        if (cards == null) {
            if (other.cards != null)
                return false;
        } else if (!cards.equals(other.cards))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Deck [cards=" + cards + "]";
    }
}
