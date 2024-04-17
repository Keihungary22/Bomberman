package model.Tile;

import model.Game;

import java.util.Random;

public class Box extends Tile {
    private enum Content {
        BOMB_POWER_UP,
        BOMB_INCREASE,
    };
    private Content content;
    private int hitPoints; // The durability of the box

    public Box(int x, int y) {
        super(x, y);
        this.destructible = true;
        this.visual = "Box.png";
        // Randomly determine the contents of this box
        Random random = new Random();
        this.content = Content.values()[random.nextInt(Content.values().length)];
        this.hitPoints = 1; // Set to be destroyed with one hit as an example
    }
    
    // Method to apply damage to the box
    public void damage() {
        hitPoints--; // Decrease durability
        if (hitPoints <= 0) {
            destroy(); // Destroy the box if durability falls to zero or below
        }
    }
    
    // Method that gets called when the box is destroyed
    public void destroy() {
        String visual = switch (content) {
            case BOMB_POWER_UP -> "item_bomb_power_up.png";
            case BOMB_INCREASE -> "item_bomb_increase.png";
        };
        Treasure newTreasure = new Treasure(x, y, visual);
        Game.treasures.add(newTreasure);
        Game.boxes.remove(this);
    }
    
    private void removeFromGame() {
        // Logic to remove this box from the game
//        Game.map.removeTile(this);
    }
}
