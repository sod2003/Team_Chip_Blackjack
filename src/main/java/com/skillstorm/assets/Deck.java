package com.skillstorm.assets;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

// Deck All cards currently in the Deck Shuffle method Deal method Card Suit Value
public class Deck {
    private Queue<Card> cards;

    public Deck(Card[] cards) {
        this.cards = new LinkedList<>(Arrays.asList(cards));
    }

    public Queue<Card> getCards() {
        return cards;
    }

    public Card draw() {
        return cards.poll();
    }

    public void shuffle() {
        Collections.shuffle((Arrays.asList(cards)));
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
