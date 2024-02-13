package com.skillstorm.logic;

import com.skillstorm.assets.Hand;

public class Rules {
    public static boolean checkSplit(Hand hand) {
        if (hand.getCardList().size() == 2 && hand.getCardList().get(0).getRank() == hand.getCardList().get(1).getRank()) {
            return true;
        }
        return false;
    }
}
