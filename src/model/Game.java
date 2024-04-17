package model;

import model.Tile.*;
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
    public static ArrayList<Treasure> treasures;
    public static void RefreshMode(){
        players = new ArrayList<>();
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        boxes = new ArrayList<>();
        treasures = new ArrayList<>();
        number_of_players = 2;
        map = new Map("SmallMap");
        number_of_rounds = 1;
        current_round = 1;
    }

    public static void refreshForRound(){
        for(Player p : players){
            p.setAlive(true);
            p.setPower_of_bombs(1);
            p.setMax_number_of_bombs(1);
            p.setCurrent_number_of_bomb(0);
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
        treasures.clear();
        //region >> set boxes on new map
        for(int i = 3; i < Game.map.getSize() - 2; i+=2){
            for(int j = 3; j < Game.map.getSize() - 2; j+=2){
                boxes.add(new Box(i, j));
            }
        }
        //endregion
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