package model.Tile;

public class Bomb extends Tile {
    private int time;
    public Bomb(int x, int y) {
        super(x, y);
        this.destructible = false;
        this.visual = "Bomb.png";
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {}
}