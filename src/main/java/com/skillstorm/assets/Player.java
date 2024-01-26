package com.skillstorm.assets;

import java.util.List;

// Player Name Earnings Hand of cards Hit method Split method Stay method etc.
public class Player {

    private String name;
    private double earnings;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, double earnings) {
        this.name = name;
        this.earnings = earnings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void hit() {
    }

    public void split() {

    }

    public void stay() {

    }

}
