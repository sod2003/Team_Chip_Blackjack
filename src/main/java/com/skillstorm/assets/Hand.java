package com.skillstorm.assets;

import java.util.LinkedList;

// Hand Cards in hand, and associated bet.
public class Hand {

    private LinkedList<Card> cards = new LinkedList<>();
    private double bet;

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

    /**
     * Draws a card
     * 
     * @param card
     */
    public void hit(Card card) {
        cards.add(card);
    }

    public Hand split() {
        Card splitCard = cards.removeLast();
        LinkedList<Card> secondStack = new LinkedList<>();
        secondStack.add(splitCard);
        Hand newHand = new Hand(secondStack);
        newHand.setBet(getBet());
        return newHand;
    }

    public void clear() {
        cards.clear();
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    @Override
    public String toString() {
        return "Hand [cards=" + cards + "]";
    }

    /**
     * Iterates through every card currently in the Hand and returns a formatted
     * ASCII
     * ART String representation of the cards in hand. Marked as private to be
     * called by
     * the Hand's show() and mask() methods.
     * 
     * @return
     */
    private String asciiHand() {
        if (cards.isEmpty() || cards == null)
            return "Hand is empty!";

        StringBuilder asciiString = new StringBuilder();
        StringBuilder row1 = new StringBuilder();
        StringBuilder row2 = new StringBuilder();
        StringBuilder row3 = new StringBuilder();
        StringBuilder row4 = new StringBuilder();
        StringBuilder row5 = new StringBuilder();
        StringBuilder row6 = new StringBuilder();

        // iterate through each card, appending/drawing each row individually
        for (Card card : cards) {
            if (card.isFaceUp()) {
                // if faceup, append each card to
                row1.append(" ____   "); // 2 spaces after each card
                if (card.getRank().getRankASCII().equals("10")) {
                    row2.append(String.format("|%s  |  ", card.getRank().getRankASCII()));
                } else {
                    row2.append(String.format("|%s   |  ", card.getRank().getRankASCII()));
                }
                row3.append(String.format("%s  ", card.getSuit().getAsciiRow3()));
                row4.append(String.format("%s  ", card.getSuit().getAsciiRow4()));
                if (card.getRank().getRankASCII().equals("10")) {
                    row5.append(String.format("| /%s|  ", card.getRank().getRankASCII()));
                } else {
                    row5.append(String.format("| / %s|  ", card.getRank().getRankASCII()));
                }
                row6.append("`----`  ");
            } else {
                // if card is face down, append face-down art to each row instead
                row1.append(" ____   ");
                row2.append("|\\  /|  ");
                row3.append("|}}{{|  ");
                row4.append("|}}{{|  ");
                row5.append("|}}{{|  ");
                row6.append("|/__\\|  ");
            }

        }
        // delete 2 spaces from end of each row
        row1.delete(row1.length() - 2, row1.length());
        row2.delete(row2.length() - 2, row2.length());
        row3.delete(row3.length() - 2, row3.length());
        row4.delete(row4.length() - 2, row4.length());
        row5.delete(row5.length() - 2, row5.length());
        row6.delete(row6.length() - 2, row6.length());

        // append line breaks onto end of each row
        row1.append("\r\n");
        row2.append("\r\n");
        row3.append("\r\n");
        row4.append("\r\n");
        row5.append("\r\n");
        row6.append("\r\n");

        // append the rows to asciiString
        asciiString.append(row1);
        asciiString.append(row2);
        asciiString.append(row3);
        asciiString.append(row4);
        asciiString.append(row5);
        asciiString.append(row6);

        return asciiString.toString();
    }

    /**
     * Sets all cards in hand to face up, then calls the asciiHand() method to
     * return an
     * ASCII Art String representation of all the cards in hand, face up.
     * 
     * @return
     */
    public String show() {
        for (Card card : cards) {
            card.setFaceUp(true);
        }
        return asciiHand();

    }

    /**
     * Sets the first card in the hand to face down, then calls the asciiHand()
     * method to return an
     * ASCII Art String representation of all the cards in hand, with the first card
     * "masked".
     * 
     * @return
     */
    public String mask() {
        cards.get(0).setFaceUp(true);
        cards.get(1).setFaceUp(false);
        for (int i = 2; i < cards.size(); i++) {
            cards.get(i).setFaceUp(true);
        }
        return asciiHand();

    }
}
