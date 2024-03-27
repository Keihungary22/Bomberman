package model;

public class Monster extends Tile {
    public Monster(int x, int y, int id, boolean isBoundary) {
        super(x, y, id, isBoundary, true, "Monster.png");
    }
}
