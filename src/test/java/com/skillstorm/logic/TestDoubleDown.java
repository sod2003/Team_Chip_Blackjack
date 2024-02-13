package com.skillstorm.logic;

import java.util.List;

import com.skillstorm.assets.Card;
import com.skillstorm.assets.Hand;
import com.skillstorm.assets.Player;

public class TestDoubleDown {
    public static void main(String[] args) {
        GameLogic gl = new GameLogic();
        gl.getDeck().shuffle();
        Player sam = new Player("Sam", 20.00);
        gl.addActivePlayer(sam);
        sam.getHand(0).setBet(20);
        List<Card> cards = Card.TestDouble();
        sam.getHand(0).hit(cards.get(0));
        sam.getHand(0).hit(cards.get(1));
        gl.getHouse().getHand().getCardList().addAll(cards);
        gl.handlePlayerTurn(sam, sam.getHand(0));
        List<Hand> samsHands = sam.getAllHands();
        System.out.println(samsHands);
    }
}
