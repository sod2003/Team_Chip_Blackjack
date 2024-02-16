package com.skillstorm.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.skillstorm.assets.Player;

public class Load {

    private static String homeDir = System.getProperty("user.home");
    private static String fileName = "BlackjackPlayers.json";
    private static String path = homeDir + File.separator + "Team_Chip_BlackJack";
    private static File customDir = new File(path);

    /**
     * Reads previous player data from a JSON file and returns it as an ArrayList of
     * Players.
     * 
     * @param player
     * @return
     */
    public static ArrayList<Player> load() {

        if (!customDir.exists()) {
            try {
                customDir.mkdirs();
            } catch (Exception e) {
                UI.printHeading(e.getMessage());
            }
        } else {
            // The path could not be created for some reason
        }

        // JSONArray to hold info from JSON file
        JSONArray playerJSONArray = new JSONArray();

        // file object to use to check if json leaderboard file exists
        File jsonFile = new File(path + fileName);

        // if leaderboard json file doesn't exist, return a blank arraylist
        if (!jsonFile.exists()) {
            System.out.println("No previous save file found. Creating save...");
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

    /**
     * Converts a JSONArray from the save file to an ArrayList of previous Players.
     * 
     * @param JSONPlayerList
     * @return leaderboardList
     */
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
     * /**
     * Checks an ArrayList (intended for the leaderboard) for an existing player and
     * returns a reference to the
     * player object if found or null if not found
     * 
     * @param name
     * @param leaderboardList
     * @return player
     */
    public static Player getReturningPlayer(String name, ArrayList<Player> leaderboardList)
            throws NoSuchElementException {

        for (Player p : leaderboardList) {
            if (p.getName().trim().equalsIgnoreCase(name.trim())) {
                return p;
            }
        }
        // if no player is found in the list matching the input name, throw an exception
        // (create new player in the catch block)
        throw new NoSuchElementException("No previous player found. Create a new one.");
    }

}
