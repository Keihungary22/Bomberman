package model;

import model.Tile.Bomb;
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
    public static void RefreshMode(){
        players = new ArrayList<>();
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();
        number_of_players = 2;
        map = new Map("SmallMap");
        number_of_rounds = 1;
        current_round = 1;
    }
    StartScreen_GUI game = new StartScreen_GUI();
}