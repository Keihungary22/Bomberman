package model.Tile;

public class Explosion extends Tile {
    public Explosion(int x, int y, String visual) {
        super(x, y);
        this.destructible = false;
        this.visual = visual;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Explosion)) {
            return false;
        }
        Explosion other = (Explosion) obj;
        return this.x == other.x && this.y == other.y;
    }
}
