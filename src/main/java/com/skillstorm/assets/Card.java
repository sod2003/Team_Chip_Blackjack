package com.skillstorm.assets;

import java.util.Arrays;
import java.util.List;

public class Card {
    private Suit suit;
    private Rank rank;
    private boolean faceUp = false;

    private Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    /**
     * Returns an array of cards based on a standard 52 card deck
     * 
     * @return 52 card array
     */
    public static Card[] generateCards() {
        Card[] cards = new Card[52];
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[index] = new Card(suit, rank);
                index++;
            }
        }
        return cards;
    }

    /**
     * Returns an ASCII Art String representation of the card.
     * 
     * @return ASCII art string of card
     */
    public String show() {
        StringBuilder cardArt = new StringBuilder();
        String rankText = this.rank.getRankASCII();

        String heart = String.format(
                "|%s   |\r\n" + //
                        "|(\\/)|\r\n" + //
                        "| \\/ |\r\n" + //
                        "|   %s|\r\n" + //
                        "`----`",
                rankText, rankText);
        String diamond = String.format(
                "|%s   |\r\n" + //
                        "| /\\ |\r\n" + //
                        "| \\/ |\r\n" + //
                        "|   %s|\r\n" + //
                        "`----`",
                rankText, rankText);
        String spade = String.format(
                "|%s   |\r\n" + //
                        "| /\\ |\r\n" + //
                        "|(__)|\r\n" + //
                        "| /\\%s|\r\n" + //
                        "`----`",
                rankText, rankText);
        String club = String.format(
                "|%s   |\r\n" + //
                        "| () |\r\n" + //
                        "|()()|\r\n" + //
                        "| /\\%s|\r\n" + //
                        "`----`",
                rankText, rankText);
        if (faceUp) {
            cardArt.append(" ____ \r\n");
            switch (this.suit) {
                case HEART:
                    cardArt.append(heart);
                    break;
                case DIAMOND:
                    cardArt.append(diamond);
                    break;
                case SPADE:
                    cardArt.append(spade);
                    break;
                case CLUB:
                    cardArt.append(club);
                    break;
            }
            // adjusting width of the card if double digit rank
            if (rankText.length() == 2) {
                cardArt.deleteCharAt(11);
                cardArt.deleteCharAt(35);

            }

        } else {
            cardArt.append(" ____\r\n" + //
                    "|\\  /|\r\n" + //
                    "|}}{{|\r\n" + //
                    "|}}{{|\r\n" + //
                    "|}}{{|\r\n" + //
                    "|/__\\|");
        }
        return cardArt.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
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

    /**
     * Used only during testing
     * 
     * @return
     */
    public static List<Card> TestCardSplit() {
        List<Card> cards = Arrays.asList(new Card(Suit.DIAMOND, Rank.ACE), new Card(Suit.CLUB, Rank.ACE));
        return cards;
    }

    /**
     * Used only during testing
     * 
     * @return
     */
    public static List<Card> TestDouble() {
        List<Card> cards = Arrays.asList(new Card(Suit.DIAMOND, Rank.FIVE), new Card(Suit.CLUB, Rank.SIX));
        return cards;
    }
}
