package com.skillstorm.assets;

public enum Rank {

    /**
     * Ranks for each suit of cards
     */

    ACE(11, "A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(10, "J"),
    QUEEN(10, "Q"),
    KING(10, "K");

    private final int rankValue; // value of each rank to use for calculations
    private final String rankASCII; // String of each rank to be plugged into Cards' ASCII art drawing

    private Rank(int rankValue, String rankASCII) {
        this.rankValue = rankValue;
        this.rankASCII = rankASCII;
    }

    public int getRankValue() {
        return this.rankValue;
    }

    public String getRankASCII() {
        return this.rankASCII;
    }

    @Override
    public String toString() {
        switch (this) {
            case ACE:
                return "Ace";
            case TWO:
                return "Two";
            case THREE:
                return "Three";
            case FOUR:
                return "Four";
            case FIVE:
                return "Five";
            case SIX:
                return "Six";
            case SEVEN:
                return "Seven";
            case EIGHT:
                return "Eight";
            case NINE:
                return "Nine";
            case TEN:
                return "Ten";
            case JACK:
                return "Jack";
            case QUEEN:
                return "Queen";
            case KING:
                return "King";
            default:
                return super.toString();
        }
    }

}
