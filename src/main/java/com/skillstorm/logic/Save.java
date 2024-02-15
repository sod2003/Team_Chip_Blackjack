package com.skillstorm.logic;

import com.skillstorm.assets.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.*;

public class Save {

    private static String fileName = "BlackjackPlayers.json";
    private static String path = "src/main/resources/";

    /**
     * Saves the player object and all previous player data into a .json file.
     * 
     * @param player
     * @param leaderboardList
     */
    @SuppressWarnings("unchecked")
    public static void save(ArrayList<Player> playerList, ArrayList<Player> leaderboardList) {

        // convert player to JSONOBject and if not in the current JSONArray of existing
        // players, they are added with their current state
        JSONArray playersJsonArray = new JSONArray();

        // if player's name is not in namesList, add them to playerArray
        for (Player plyr : playerList) {
            if (!leaderboardList.contains(plyr)) {
                leaderboardList.add(plyr);
            }
        }

        // convert each player in the playerArray to a JSONObject and store them in a
        // JSONArray
        for (Player p : leaderboardList) {
            JSONObject pJSON = p.toJSONObject();
            playersJsonArray.add(pJSON);
        }

        // write to JSON file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path +
                fileName))) {
            playersJsonArray.writeJSONString(writer);
            UI.printHeading("Player data saved.");
            Thread.sleep(1000);
            UI.clearConsole();
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
