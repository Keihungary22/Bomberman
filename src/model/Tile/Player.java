package model.Tile;

public class Player extends Tile {
    public int score = 0;
    public Player() {
        super(0, 0);
        this.x = 0;
        this.y = 0;
        this.destructible = true;
        this.visual = "Player.png";
    }

    public void moveUp(){
        this.y -= 1;
    }
    public void moveDown(){
        this.y += 1;
    }
    public void moveRight(){
        this.x += 1;
    }
    public void moveLeft(){
        this.x -= 1;
    }

    public int getScore(){
        return score;
    }
}