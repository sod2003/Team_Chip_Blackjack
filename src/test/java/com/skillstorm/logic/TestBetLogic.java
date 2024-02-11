package com.skillstorm.logic;

import com.skillstorm.assets.Player;

public class TestBetLogic {
    public static void main(String[] args) {
        GameLogic gl = new GameLogic();
        gl.addPlayer(new Player("Josh", 20));
        System.out.println(gl.getPlayerList().get(0).getEarnings());
        gl.takeBets();
        System.out.println(gl.getPlayerList().get(0).getEarnings());
        
    }
}
