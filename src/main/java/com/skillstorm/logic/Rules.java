package com.skillstorm.logic;

import com.skillstorm.assets.Hand;
import com.skillstorm.assets.Rank;

public class Rules {
    public static boolean checkSplit(Hand hand) {
        if (hand.getCardList().size() == 2 && hand.getCardList().get(0).getRank() == hand.getCardList().get(1).getRank()) {
            return true;
        }
        return false;
    }

    public static boolean checkInsurance(Hand dealerHand) {
        if (dealerHand.getCardList().getFirst().getRank() == Rank.ACE) {
            return true;
        }
        return false;
    }

    public static boolean checkDouble(Hand hand) {
        int total = hand.total();
        if (total == 9 || total == 10 || total == 11) {
            return true;
        }
        return false;
    }
}
