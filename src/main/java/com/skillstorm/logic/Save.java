package com.skillstorm.logic;

import com.skillstorm.assets.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

import org.json.simple.*;

public class Save {

    private static String fileName = "BlackjackPlayers.json";
    private static String path = "./";

    /**
     * Saves the player object and all previous player data (stored in memory as a
     * JSONArray) to a JSON file.
     * 
     * @param player
     * @param playerArray
     */
    @SuppressWarnings("unchecked")
    public static void save(Player player, JSONArray playerArray) {

        // convert player to JSONOBject and if not in the current JSONArray of existing
        // players, they are added with their current state
        JSONObject playerJSON = player.toJSONObject();
        if (!playerArray.contains(playerJSON.get("name"))) {
            playerArray.add(playerJSON);
        }

        // if player is already in existing players, update their earnings

        for (Object object : playerArray) {
            if (object instanceof JSONObject) {
                JSONObject jo = (JSONObject) object;
                if (jo.containsValue(player.getName().toLowerCase())) {
                    jo.put("earnings", player.getEarnings());
                }
            }
            System.out.println("object: " + object);

        }

        // write to JSON file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName))) {
            playerArray.writeJSONString(writer);
            System.out.println("Player data saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static void save2(Player player, List<Player> playerList) {

        // If player already in list, update their earnings in the existing entry
        // If player not in list, add them
        if (playerList.size() < 1) {
            playerList.add(player);
        }
        if (player != null && playerList != null) {
            for (Player p : playerList) {
                if (p.getName().equalsIgnoreCase(player.getName())) {
                    p.setEarnings(player.getEarnings());
                } else {
                    playerList.add(player);
                }
            }
        }

        // create a JSON array and add all players from playerList while converting them
        // to JSON Objects
        JSONArray ja = new JSONArray();
        for (Player p : playerList) {
            ja.add(p.toJSONObject());
            System.out.println("in jsonArray during save" + ja);
        }

        // write to JSON file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName))) {
            ja.writeJSONString(writer);
            System.out.println("Player data saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
