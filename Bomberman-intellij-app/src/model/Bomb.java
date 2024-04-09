package model;

public class Bomb extends Tile {
    public Bomb(int x, int y) {
        super(x, y);
        this.destructible = false;
        this.visual = "Bomb.png";
    }
}