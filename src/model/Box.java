package model;

public class Box extends Tile {
    public Box(int x, int y) {
        super(x, y);
        this.destructible = true;
        this.visual = "Box.png";
    }
}