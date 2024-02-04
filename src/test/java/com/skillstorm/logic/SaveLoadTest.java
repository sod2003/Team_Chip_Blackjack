package com.skillstorm.logic;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;

import com.skillstorm.assets.Player;

public class SaveLoadTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        /*
         * USING SAVE AND LOAD
         */

        // load existing playerlist
        System.out.println("LOADING...");
        JSONArray playerList = Load.load();
        System.out.println("LOAD COMPLETE");

        // create a new player
        Player testPlayer4 = new Player("Josh");

        // win some money
        testPlayer4.setEarnings(testPlayer4.getEarnings() + 100.00);

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
        testPlayer5.setEarnings(testPlayer5.getEarnings() + 100.00);

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

        // /*
        // * USING SAVE2 AND LOAD2
        // */
        // // load existing playerlist
        // System.out.println("LOADING...");
        // ArrayList<Player> playerList2 = Load.loadPlayerData2();
        // System.out.println("LOAD COMPLETE");

        // // create a new player
        // Player testPlayer = new Player("Josh");

        // // win some money
        // testPlayer.setEarnings(testPlayer.getEarnings() + 100.00);

        // // save player status once over
        // System.out.println("SAVING...");
        // Save.save2(testPlayer, playerList2);
        // System.out.println("SAVING COMPLETE");

        // // Load playerlist again
        // System.out.println("LOADING...");
        // playerList2 = Load.loadPlayerData2();
        // System.out.println("LOAD COMPLETE");

        // // create a new player
        // Player testPlayer2 = new Player("Sean");

        // // win some money
        // testPlayer2.setEarnings(testPlayer2.getEarnings() + 100.00);

        // // save player status once over
        // System.out.println("SAVING...");
        // Save.save2(testPlayer2, playerList2);
        // System.out.println("SAVING COMPLETE");

        // // Load playerlist again
        // System.out.println("LOADING...");
        // playerList2 = Load.loadPlayerData2();
        // System.out.println("LOAD COMPLETE");

        // // create a new player
        // Player testPlayer3 = new Player("Josh");

        // // win some money
        // testPlayer3.setEarnings(testPlayer3.getEarnings() + 100.00);

        // // save player status once over
        // System.out.println("SAVING...");
        // Save.save2(testPlayer3, playerList2);
        // System.out.println("SAVING COMPLETE");

    }
}
