package model;

import java.util.Random;

public class Box extends Tile {
    private boolean containsPowerUp;
    private int hitPoints; // The durability of the box

    public Box(int x, int y) {
        super(x, y);
        this.destructible = true;
        this.visual = "Box.png";
        // Randomly determine whether the box contains a power-up
        this.containsPowerUp = new Random().nextBoolean();
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
        if (containsPowerUp) {
            // Logic to spawn a power-up
        }
        // Implement logic to remove the box from the game
        Game.map.removeTile(this);
    }
    
    private void removeFromGame() {
        // Logic to remove this box from the game
        Game.map.removeTile(this);
    }
}
