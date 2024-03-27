package model;

public class Player extends Tile {
    private static int score = 0;
    public Player(int x, int y, int id, boolean isBoundary) {
        super(x, y, id, isBoundary, true, "Player.png");
    }
    public static int getScore(){
        return score;
    }
}