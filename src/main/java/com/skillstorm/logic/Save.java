package com.skillstorm.logic;

import com.skillstorm.assets.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.*;

public class Save {

    private static String fileName = "BlackjackPlayers.json";
    private static String path = "./";

    /**
     * Saves the player object and all previous player data into a .json file.
     * 
     * @param player
     * @param playerArray
     */
    @SuppressWarnings("unchecked")
    public static void save(Player player, ArrayList<Player> playerArray) {

        // convert player to JSONOBject and if not in the current JSONArray of existing
        // players, they are added with their current state
        JSONArray playersJsonArray = new JSONArray();

        // if player's name is not in namesList, add them to playerArray
        if (!playerArray.contains(player)) {
            playerArray.add(player);
        }

        // print playerArray for debug purposes
        System.out.println(playerArray);

        // convert each player in the playerArray to a JSONObject and store them in a
        // JSONArray
        for (Player p : playerArray) {
            JSONObject pJSON = p.toJSONObject();
            playersJsonArray.add(pJSON);
        }

        // write to JSON file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path +
                fileName))) {
            playersJsonArray.writeJSONString(writer);
            System.out.println("Player data saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static HashMap<String, HashMap<HashMap<String, String>, HashMap<String, Double>>> convertArrayListToHashMap(
            ArrayList<Player> playerList) {
        HashMap<String, HashMap<HashMap<String, String>, HashMap<String, Double>>> mapForJSON = new HashMap<>();
        for (Player player : playerList) {
            HashMap<HashMap<String, String>, HashMap<String, Double>> mapOfPlayer = new HashMap<>();
            HashMap<String, String> mapName = new HashMap<>();
            HashMap<String, Double> mapEarnings = new HashMap<>();
            mapName.put("name", player.getName());
            mapEarnings.put("earnings", player.getEarnings());
            mapOfPlayer.put(mapName, mapEarnings);
            mapForJSON.put(player.getName(), mapOfPlayer);
        }
        return mapForJSON;

    }

}
