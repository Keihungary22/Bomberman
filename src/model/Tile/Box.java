package model.Tile;

import model.Game;

import java.util.Random;

public class Box extends Tile {
    private enum Content {
        BOMB_POWER_UP,
        BOMB_INCREASE,
        INVINCIBILITY,
        GHOST,
        DETONATOR,
        ROLLERSKATE,
        OBSTACLE
    };
    private Content content;

    public Box(int x, int y) {
        super(x, y);
        this.destructible = true;
        this.visual = "Box.png";
        // Randomly determine the contents of this box
        Random random = new Random();
        this.content = Content.values()[random.nextInt(Content.values().length)];
    }
    
    // Method that gets called when the box is destroyed
    public void destroy() {
        String visual = switch (content) {
            case BOMB_POWER_UP -> "item_bomb_power_up.png";
            case BOMB_INCREASE -> "item_bomb_increase.png";
            case INVINCIBILITY -> "item_invincibility.png";
            case GHOST -> "item_ghost.png";
            case DETONATOR -> "item_detonator.png";
            case ROLLERSKATE -> "item_rollerskate.png";
            case OBSTACLE -> "item_obstacle.png";
        };
        Treasure newTreasure = new Treasure(x, y, visual);
        Game.treasures.add(newTreasure);
        Game.boxes.remove(this);
    }

}
