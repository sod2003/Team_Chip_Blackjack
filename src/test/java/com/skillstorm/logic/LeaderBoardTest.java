package com.skillstorm.logic;

import java.util.ArrayList;

import com.skillstorm.assets.Player;

public class LeaderBoardTest {

    public static void main(String[] args) {
        GameLogic gl = new GameLogic();
        ArrayList<Player> playerList = Load.load();
        playerList.add(new Player("alex", 0.00));
        playerList.add(new Player("Zodd", 300.00));

        gl.printLeaderboard(playerList);

    }

}
