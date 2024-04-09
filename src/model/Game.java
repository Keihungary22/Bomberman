package model;

import view.*;

import javax.swing.*;
import java.util.ArrayList;

public class Game {
    public static int number_of_players;
    public static Map map;
    public static int number_of_rounds;
    public static int current_round;
    public static ArrayList<Player> players;
    public static void RefreshMode(){
        players = new ArrayList<>();
        number_of_players = 2;
        map = new Map("SmallMap");
        number_of_rounds = 1;
        current_round = 1;
    }
    StartScreen_GUI game = new StartScreen_GUI();
//    public static void main(String[] args) {
//        StartScreen_GUI game = new StartScreen_GUI();
//    }
}