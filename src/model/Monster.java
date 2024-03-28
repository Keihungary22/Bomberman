package model;

public class Monster extends Tile {
    public Monster(int x, int y) {
        super(x, y);
        this.destructible = true;
        this.visual = "Monster.png";
    }
}
