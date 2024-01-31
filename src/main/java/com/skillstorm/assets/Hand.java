package com.skillstorm.assets;

import java.util.LinkedList;

// Hand Cards in hand
public class Hand {

    private LinkedList<Card> cards = new LinkedList<>();

    public Hand() {
    }

    public Hand(LinkedList<Card> cards) {
        this.cards = cards;
    }

    public LinkedList<Card> getCardList() {
        return cards;
    }

    /**
     * Totals the value of all cards in the Hand.
     * If the total is over 21, the hand is checked for aces and the total is
     * dropped by 10 for each ace until total <= 21 if possible.
     * 
     * @return
     */
    public int total() {
        int total = 0;

        for (Card card : cards) {
            total += card.getRank().getRankValue();
        }
        if (total > 21) {
            for (Card card : cards) {
                if (card.getRank().equals(Rank.ACE) && total > 21) {
                    total -= 10;
                }
            }
        }
        return total;
    }

    public void hit(Deck deck) {
        cards.add(deck.draw());
    }

    public void split() {
        // TODO add split method to Hand
    }

    public void stay() {
        // TODO add stay method to hand
    }

    @Override
    public String toString() {
        return "Hand [cards=" + cards + "]";
    }

}
