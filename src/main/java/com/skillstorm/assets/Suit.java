package com.skillstorm.assets;

public enum Suit {
    HEART, DIAMOND, SPADE, CLUB;

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