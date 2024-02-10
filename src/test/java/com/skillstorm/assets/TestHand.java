package com.skillstorm.assets;

import com.skillstorm.logic.GameLogic;

public class TestHand {

    public static void main(String[] args) {

        GameLogic gl = new GameLogic();
        Player player = new Player("Josh");
        gl.addPlayer(player);
        gl.getDeck().shuffle();
        gl.deal();
        player.getHand().hit(gl.getDeck().draw());
        player.getHand().hit(gl.getDeck().draw());
        player.getHand().hit(gl.getDeck().draw());
        System.out.println(player.getHand().show());
        System.out.println(player.getHand().mask());

    }
}
