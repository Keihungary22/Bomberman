package model.Tile;

import javax.swing.plaf.TreeUI;

public class Player extends Tile {
    private int score = 0;
    private boolean is_alive = true;

    public Player(String visual) {
        super(0, 0);
        this.x = 0;
        this.y = 0;
        this.destructible = true;
        this.visual = visual;
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
    public Bomb putBomb(){
        return new Bomb(this.x,this.y);
    }

    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }

    public boolean isAlive() {
        return is_alive;
    }
    public void setAlive(boolean is_alive) {
        this.is_alive = is_alive;
    }
}