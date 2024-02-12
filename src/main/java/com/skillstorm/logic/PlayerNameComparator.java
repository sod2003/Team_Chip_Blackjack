package com.skillstorm.logic;

import java.util.Comparator;

import com.skillstorm.assets.Player;

public class PlayerNameComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return (o1.getName().hashCode() - o2.getName().hashCode());
    }

}