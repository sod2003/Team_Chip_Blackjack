package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.Random;

import com.skillstorm.assets.Player;

public class SaveLoadTest {

    public static void main(String[] args) {

        /*
         * USING SAVE AND LOAD
         */

        // load existing playerlist
        System.out.println("LOADING...");
        ArrayList<Player> playerList = Load.load();
        System.out.println("LOAD COMPLETE");
        System.out.println("playerList: " + playerList);

        // create a new player
        Player testPlayer4 = new Player("Josh");

        // win some money
        testPlayer4.setEarnings(200.00);

        // save player status once over
        System.out.println("SAVING...");
        Save.save(testPlayer4, playerList);
        System.out.println("SAVING COMPLETE");

        // Load playerlist again
        System.out.println("LOADING...");
        playerList = Load.load();
        System.out.println("LOAD COMPLETE");

        // create a new player
        Player testPlayer5 = new Player("Sean");

        // win some money
        testPlayer5.setEarnings(testPlayer5.getEarnings() + new Random().nextInt(20));

        // save player status once over
        System.out.println("SAVING...");
        Save.save(testPlayer5, playerList);
        System.out.println("SAVING COMPLETE");

        // Load playerlist again
        System.out.println("LOADING...");
        playerList = Load.load();
        System.out.println("LOAD COMPLETE");

        // create a new player
        Player testPlayer6 = new Player("Josh");

        // win some money
        testPlayer6.setEarnings(testPlayer6.getEarnings() + 100.00);

        // save player status once over
        System.out.println("SAVING...");
        Save.save(testPlayer6, playerList);
        System.out.println("SAVING COMPLETE");

    }
}
