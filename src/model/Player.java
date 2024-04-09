package model;

import java.awt.event.KeyEvent;
import java.util.Random;

public class Player extends Tile {
    public int score = 0;
    private Random random = new Random();
    public Player() {
        super(0, 0);
        this.x = findFreeTile().x;
        this.y = findFreeTile().y;
        this.destructible = true;
        this.visual = "Player.png";
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                this.y += 1;
                break;
            case KeyEvent.VK_S:
                this.y -= 1;
                break;
            case KeyEvent.VK_D:
                this.x += 1;
                break;
            case KeyEvent.VK_A:
                this.x -= 1;
                break;
        }
    }

    public int getScore(){
        return score;
    }
    private Tile findFreeTile(){
        return Game.map.getFreeFields().get(random.nextInt(Game.map.getFreeFields().size()));
    }
}