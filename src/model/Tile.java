package model;

public class Tile {
    protected int x;
    protected int y;
    protected boolean isBoundary;
    protected boolean destructible;
    protected String visual;
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
//        this.isBoundary = false;
        if (Game.map != null && (this.x == 0 || this.y == 0 || this.x == Game.map.getSize()-1 || this.y == Game.map.getSize()-1)) {
            this.isBoundary = true;
        } else {
            this.isBoundary = false;
        }
    }
}
