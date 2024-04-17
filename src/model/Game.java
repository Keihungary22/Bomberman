package model;

import model.Tile.Bomb;
import model.Tile.Box;
import model.Tile.Explosion;
import model.Tile.Player;
import view.*;

import java.util.ArrayList;

public class Game {
    public static int number_of_players;
    public static Map map;
    public static int number_of_rounds;
    public static int current_round;
    public static ArrayList<Player> players;
    public static ArrayList<Bomb> bombs;
    public static ArrayList<Explosion> explosions;
    public static ArrayList<Box> boxes;
    public static void RefreshMode(){
        players = new ArrayList<>();
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        boxes = new ArrayList<>();
        number_of_players = 2;
        map = new Map("SmallMap");
        number_of_rounds = 1;
        current_round = 1;
    }

    public static void refreshForRound(){
        for(Player p : players){
            p.setAlive(true);
        }
        for(Bomb bomb : bombs){
            bomb.getTimer().cancel();
        }
        for(Explosion exp : explosions){
            exp.getOwner_bomb().getExp_timer().cancel();
        }
        bombs.clear();
        explosions.clear();
        boxes.clear();
        boxes.add(new Box(5, 5));
        boxes.add(new Box(5, 7));
        boxes.add(new Box(7, 5));
        boxes.add(new Box(7, 7));
    }

    public static int getNumberOfAlivePlayers(){
        int alivePlayers = 0;
        for(Player player : Game.players){
            if(player.isAlive()){
                alivePlayers++;
            }
        }
        return alivePlayers;
    }

    public Game(){
        StartScreen_GUI game = new StartScreen_GUI();
    }
}