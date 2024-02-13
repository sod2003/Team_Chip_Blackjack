package com.skillstorm.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

// Player Name Earnings Hand of cards Hit method Split method Stay method etc.
public class Player {

    private String name;
    private double earnings;
    private List<Hand> hands = new ArrayList<Hand>();
    private final double DEFAULTSTARTINGEARNINGS = 500;

    public Player(String name) {
        this.name = name;
        this.earnings = DEFAULTSTARTINGEARNINGS;
        addNewHand();
    }

    public Player(String name, double earnings) {
        this.name = name;
        this.earnings = earnings;
        addNewHand();
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

    public void decreaseEarnings(double bet) {
        earnings -= bet;
    }

    public void increaseEarnings(double winnings) {
        earnings += winnings;
    }

    public Hand getHand(int index) throws IllegalArgumentException {
        if (hands.size() <= index || index < 0) {
            throw new IllegalArgumentException();
        }
        return hands.get(index);
    }

    public List<Hand> getAllHands() {
        return hands;
    }

    public void addNewHand() {
        hands.add(new Hand());
    }

    public void addNewHand(Hand hand) {
        hands.add(hand);
    }

    public void dropHands() {
        hands.clear();
        addNewHand();
    }

    /**
     * Converts a player object to a JSON
     * 
     * @param player
     * @return
     */
    public JSONObject toJSONObject() {

        // store player data in a Hashmap for use in the JSONObject constructor
        HashMap<String, Object> playerDetails = new HashMap<>();
        playerDetails.put("name", name);
        playerDetails.put("earnings", Double.valueOf(earnings));

        return new JSONObject(playerDetails);

    }

    @Override
    public String toString() {
        return "Player " + name + ", earnings: " + earnings + ", hand: " + hands + ".";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(earnings);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((hands == null) ? 0 : hands.hashCode());
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
        Player other = (Player) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(earnings) != Double.doubleToLongBits(other.earnings))
            return false;
        if (hands == null) {
            if (other.hands != null)
                return false;
        } else if (!hands.equals(other.hands))
            return false;
        return true;
    }

}
