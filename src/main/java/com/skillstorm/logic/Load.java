package com.skillstorm.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.skillstorm.assets.Player;

public class Load {

    private static String fileName = "BlackjackPlayers.json";
    private static String path = "./";

    /**
     * Reads previous player data from a JSON file and returns it as an ArrayList of
     * Players.
     * 
     * @param player
     * @return
     */
    public static ArrayList<Player> load() {

        // JSONArray to hold info from JSON file
        JSONArray playerJSONArray = new JSONArray();

        // file object to use to check if json leaderboard file exists
        File jsonFile = new File(path + fileName);

        // if leaderboard json file doesn't exist, return a blank arraylist
        if (!jsonFile.exists()) {
            System.out.println("file exists");
            return new ArrayList<Player>();
        }
        // read from JSON file
        try (BufferedReader reader = new BufferedReader(new FileReader(path +
                fileName))) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            // Store object from reading JSON file in JSONArray
            playerJSONArray = (JSONArray) obj;
            // report on results
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Player> newPlayerList = convertJSONArrayToPlayerList(playerJSONArray);

        // return stored ArrayList to be held until game save
        return newPlayerList;
    }

    public static ArrayList<Player> convertJSONArrayToPlayerList(JSONArray JSONPlayerList) {
        ArrayList<Player> newPlayerList = new ArrayList<>();
        for (Object o : JSONPlayerList) {
            String newName = "";
            double newEarnings = 0.0;
            // convert each object in the JSONArray to a JSONObject, pull the name and
            // earnings data and use it to construct a new Player object
            if (o instanceof JSONObject) {
                JSONObject jo = (JSONObject) o;
                if (jo.containsKey("name")) {
                    newName = jo.get("name").toString();
                }
                if (jo.containsKey("earnings")) {
                    newEarnings = Double.valueOf(jo.get("earnings").toString());
                }
                Player newPlayer = new Player(newName, newEarnings);
                newPlayerList.add(newPlayer);
            }
        }
        return newPlayerList;
    }

    /**
     * Checks an ArrayList for an existing player and returns a reference to the
     * player object if found or null if not found
     * 
     * @param name
     * @param leaderboardList
     * @return
     */
    public static Player getReturningPlayer(String name, ArrayList<Player> leaderboardList) {

        for (Player p : leaderboardList) {
            if (p.getName().trim().equalsIgnoreCase(name.trim())) {
                return p;
            } else {
                continue;
            }
        }

        return new Player(name);
    }

    // /**
    // * NOT CURRENTLY USED--Checks a JSONObject for a matching String key and
    // returns the value
    // *
    // * @param doc
    // * @param key
    // * @return
    // */
    // private JSONObject getJSONObjectFromFile(JSONObject doc, String key) {
    // if (doc.containsKey(key)) {
    // Object simpleObject = doc.get(key);
    // if (simpleObject instanceof JSONObject) {
    // return (JSONObject) simpleObject;
    // }
    // }
    // return null;
    // }

}
