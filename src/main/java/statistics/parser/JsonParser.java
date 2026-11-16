package statistics.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import game.entities.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JsonParser {
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args){
        //Test:
        JsonParser parser = new JsonParser();
        Player player = new Player("Niklas", 50000);
        boolean sucess = parser.createPlayerJson(player);

        boolean contains = parser.isPlayer("Niklas");

        Player player2 = new Player("Test", 1);
        sucess = parser.createPlayerJson(player2);
        System.out.println(sucess);
    }

    public boolean createPlayerJson(Player player){
        List<Player> players = getPlayers();
        if (players == null){
            players = new ArrayList<>();
        }
        players.add(player);

        return writePlayers(players);
    }

    public boolean writeScore(Player player){
        List<Player> players = getPlayers();

        for (Player listPlayers : players){
            if (listPlayers.getName().equals(player.getName())){
                listPlayers.setHighscore(player.getHighscore());
            }
        }
        return writePlayers(players);
    }

    public boolean isPlayer(String name){
        List<Player> players = getPlayers();
        if (players == null){
            return false;
        }
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean writePlayers(List<Player> players){
        try {
            FileWriter writer = new FileWriter("src/main/java/statistics/parser/jsons/player.json");
            gson.toJson(players, writer);
            writer.close();
        }catch (IOException e) {
            //return false;
            throw new RuntimeException(e);
        }
        return true;
    }

    private List<Player> getPlayers(){
        List<Player> players;

        try {
            FileReader reader = new FileReader("src/main/java/statistics/parser/jsons/player.json");
            TypeToken<List<Player>> typeToken = new TypeToken<List<Player>>(){};
            players = gson.fromJson(reader, typeToken);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
