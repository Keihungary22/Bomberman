package model;

public class Tile {
    protected int x;
    protected int y;
    protected int id;
    protected boolean isBoundary;
    protected boolean destructible;
    protected String visual;
    public Tile(int x, int y, int id, boolean isBoundary, boolean destructible, String visual) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.isBoundary = isBoundary;
        this.destructible = destructible;
        this.visual = visual;
    }
//    public boolean isBoundary() {
//        return true;
//    }
}
