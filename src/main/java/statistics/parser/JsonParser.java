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
        JsonParser parser = new JsonParser();
        Player player = new Player("Niklas", 50000);
        System.out.println("ich versuche es jetzt");
        boolean sucess = parser.createPlayerJson(player);

        boolean contains = parser.isPlayer("Niklas");
        System.out.println("Schon vorhanden?: " + contains);

        Player player2 = new Player("Test", 1);
        sucess = parser.createPlayerJson(player2);
        System.out.println(sucess);
    }

    public boolean createPlayerJson(Player player){
        System.out.println("ich werde jetzt versuche die Liste zu holen");
        List<Player> players = getPlayers();
        if (players == null){
            players = new ArrayList<>();
        }
        players.add(player);


        System.out.println("Dann wird jetzt die neue Liste geschrieben");
        try {
            FileWriter writer = new FileWriter("src/main/java/statistics/parser/jsons/player.json");
            gson.toJson(players, writer);
            writer.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Auch geschafft");

        return true;
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

    //kann nach testen private gestellt werden
    public List<Player> getPlayers(){
        List<Player> players;

        System.out.println("nun wird der Versuch gestartet, das File zu lesen");
        try {
            FileReader reader = new FileReader("src/main/java/statistics/parser/jsons/player.json");
            TypeToken<List<Player>> typeToken = new TypeToken<List<Player>>(){};
            players = gson.fromJson(reader, typeToken);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("JUHU File wurde gelesen");
        return players;
    }
}
