package com.skillstorm.assets;

import com.skillstorm.logic.GameLogic;

public class TestHand {

    public static void main(String[] args) {

        GameLogic gl = new GameLogic();
        Player player = new Player("Josh");
        gl.addActivePlayer(player);
        gl.getDeck().shuffle();
        gl.deal();
        player.getHand(0).hit(gl.getDeck().draw());
        player.getHand(0).hit(gl.getDeck().draw());
        player.getHand(0).hit(gl.getDeck().draw());
        System.out.println(player.getHand(0).show());
        System.out.println(player.getHand(0).mask());

    }
}
