package com.skillstorm.logic;

import com.skillstorm.assets.Hand;
import com.skillstorm.assets.Player;
import com.skillstorm.assets.Rank;

public class Rules {

    /**
     * Check if a hand contains doubles that can be split
     * 
     * @param hand to be checked
     * @return true if hand can be split
     */
    public static boolean checkSplit(Player player, Hand hand) {
        if (hand.getCardList().size() == 2
                && hand.getCardList().get(0).getRank() == hand.getCardList().get(1).getRank()
                && (player.getEarnings() >= hand.getBet() * 2.0)) {
            return true;
        }
        return false;
    }

    /**
     * Check if an insurance bet is possible
     * 
     * @param dealerHand the house's hand
     * @return true if an insurance bet can be placed
     */
    public static boolean checkInsurance(Hand dealerHand) {
        if (dealerHand.getCardList().getFirst().getRank() == Rank.ACE) {
            return true;
        }
        return false;
    }

    /**
     * Check if the player can double down according to the rules (hand value is 9,
     * 10 or 11)
     * 
     * @param hand player hand
     * @return true if player can double down
     */
    public static boolean checkDouble(Hand hand) {
        int total = hand.total();
        if (total == 9 || total == 10 || total == 11) {
            return true;
        }
        return false;
    }

}
