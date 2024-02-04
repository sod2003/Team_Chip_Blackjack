package com.skillstorm.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.skillstorm.assets.Player;

public class Load {

    private static String fileName = "BlackjackPlayers.json";
    private static String path = "";

    /**
     * Reads previous player data from a JSON file and returns it as an ArrayList of
     * Players.
     * 
     * @param player
     * @return
     */
    public static JSONArray load() {

        JSONArray playerJSONArray = new JSONArray();

        try (BufferedReader reader = new BufferedReader(new FileReader(path +
                fileName))) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            playerJSONArray = (JSONArray) obj;
            // System.out.println("json: " + playerJSONArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerJSONArray;
    }

    /**
     * Reads previous player data from a JSON file and returns it as an ArrayList
     * of
     * Players.
     *
     * @param player
     * @return
     */
    public static ArrayList<Player> loadPlayerData2() {

        ArrayList<Player> playerList = new ArrayList<>();
        JSONArray playerJSONArray = new JSONArray();

        try (BufferedReader reader = new BufferedReader(new FileReader(path +
                fileName))) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            System.out.println("in load method, parsed json: " + obj);
            playerJSONArray = (JSONArray) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object o : playerJSONArray) {
            JSONObject jo = (JSONObject) o;
        }

        return playerList;
    }

    public static Player getReturningPlayer(String name, JSONArray playerData) {

        String retrievedName = "";
        double retrievedEarnings = 0.0;

        for (Object object : playerData) {
            JSONObject obj = (JSONObject) object;
            if (obj.get("name").toString().equalsIgnoreCase(name)) {
                retrievedName = obj.get("name").toString();
                retrievedEarnings = (double) obj.get("earnings");
            }
        }

        return new Player(retrievedName, retrievedEarnings);
    }

    private JSONObject getJSONObjectFromFile(JSONObject doc, String key) {
        if (doc.containsKey(key)) {
            Object simpleObject = doc.get(key);
            if (simpleObject instanceof JSONObject) {
                return (JSONObject) simpleObject;
            }
        }
        return null;
    }

}
