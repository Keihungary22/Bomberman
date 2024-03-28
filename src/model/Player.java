package model;

import java.util.Random;

public class Player extends Tile {
    public int score = 0;
    private Random random = new Random();
    public Player() {
        super(0, 0);
        this.x = findFreeTile().x;
        this.y = findFreeTile().y;
        this.isBoundary = findFreeTile().isBoundary;
        this.destructible = true;
        this.visual = "Player.png";
    }
    public int getScore(){
        return score;
    }
    private Tile findFreeTile(){
//        return Game.map.getFreeFields().get(random.nextInt(Game.map.getFreeFields().size()));
        return Game.map.getFreeFields().get(0);
    }
}