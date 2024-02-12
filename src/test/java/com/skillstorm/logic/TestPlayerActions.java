package com.skillstorm.logic;

import com.skillstorm.assets.Player;

public class TestPlayerActions {
    public static void main(String[] args) {
        GameLogic gl = new GameLogic();
        gl.getDeck().shuffle();
        Player sam = new Player("Sam", 20.00);
        gl.addActivePlayer(sam);
        gl.addBet(sam, 20.00);
        gl.deal();
        System.out.println(sam);
        gl.handlePlayerTurn(sam, sam.getHand(0));
        System.out.println(sam);
        System.out.println(gl.getHouse());
    }
}
