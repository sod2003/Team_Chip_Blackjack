package com.skillstorm.assets;

import java.util.HashSet;

// House (the Computer) Hand of cards Earnings Hit method Stay method etc.
public class House {

    private String name;
    private HashSet<Card> hand;
    private double earnings;

    public House() {
    }

    public House(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Card> getHand() {
        return hand;
    }

    public void setHand(HashSet<Card> hand) {
        this.hand = hand;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        return "House [name=" + name + ", hand=" + hand + ", earnings=" + earnings + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((hand == null) ? 0 : hand.hashCode());
        long temp;
        temp = Double.doubleToLongBits(earnings);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        House other = (House) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (hand == null) {
            if (other.hand != null)
                return false;
        } else if (!hand.equals(other.hand))
            return false;
        if (Double.doubleToLongBits(earnings) != Double.doubleToLongBits(other.earnings))
            return false;
        return true;
    }

}
