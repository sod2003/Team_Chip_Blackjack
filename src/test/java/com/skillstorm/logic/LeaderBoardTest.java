package com.skillstorm.logic;

import com.skillstorm.assets.Player;

public class LeaderBoardTest {

    public static void main(String[] args) {
        GameLogic gl = new GameLogic();

        System.out.println("Trying with an empty leaderboard/list:");
        gl.printLeaderboard();
        gl.addLeaderboardPlayer(new Player("alex", 0.00));
        gl.addLeaderboardPlayer(new Player("Zodd", 300.00));

        gl.printLeaderboard();

    }

}
