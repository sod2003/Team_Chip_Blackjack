package com.skillstorm.logic;

import java.util.Comparator;

import com.skillstorm.assets.Player;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return (int) (o1.getEarnings() - o2.getEarnings());
    }

}
