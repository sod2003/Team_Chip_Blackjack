package com.skillstorm.assets;

public enum Suit {

    /**
     * Suits to be used for Cards
     */

    HEART("|(\\/)|", "| \\/ |"),
    DIAMOND("| /\\ |", "| \\/ |"),
    SPADE("| /\\ |", "|(__)|"),
    CLUB("| () |", "|()()|");

    // These rows are to be used by the Hand.asciiString() method
    private final String asciiRow3;
    private final String asciiRow4;

    private Suit(String asciiRow3, String asciiRow4) {
        this.asciiRow3 = asciiRow3;
        this.asciiRow4 = asciiRow4;
    }

    public String getAsciiRow3() {
        return asciiRow3;
    }

    public String getAsciiRow4() {
        return asciiRow4;
    }

    @Override
    public String toString() {
        switch (this) {
            case HEART:
                return "Hearts";
            case DIAMOND:
                return "Diamonds";
            case SPADE:
                return "Spades";
            case CLUB:
                return "Clubs";
            default:
                return super.toString();
        }
    }
}