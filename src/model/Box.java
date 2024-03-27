package model;

public class Box extends Tile {
    public Box(int x, int y, int id, boolean isBoundary) {
        super(x, y, id, isBoundary, true, "Box.png");
    }
}