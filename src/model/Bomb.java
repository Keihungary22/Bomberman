package model;

public class Bomb extends Tile {
    public Bomb(int x, int y, int id, boolean isBoundary) {
        super(x, y, id, isBoundary, false, "Bomb.png");
    }
}