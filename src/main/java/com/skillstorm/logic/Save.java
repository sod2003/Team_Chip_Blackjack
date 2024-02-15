package com.skillstorm.logic;

import com.skillstorm.assets.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import org.json.simple.*;

public class Save {

    private static String fileName = "BlackjackPlayers.json";
    private static String path = "src/main/resources/";

    /**
     * Saves the player object and all previous player data into a .json file.
     * 
     * @param playerList      Current list of players to be checked
     * @param leaderboardList leaderboardList of long-term saved players
     */
    @SuppressWarnings("unchecked")
    public static void save(ArrayList<Player> playerList, ArrayList<Player> leaderboardList) {

        // JSONArray to store players in and subsequently write to json save file
        JSONArray playersJsonArray = new JSONArray();

        // if player's name is not in namesList, add them to leaderboardList
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

}
