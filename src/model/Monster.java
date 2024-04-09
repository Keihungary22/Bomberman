package model;

import java.util.Random;

public class Monster extends Tile {
    
    private int speed;
    private Direction direction;
    private Random random = new Random();
    
    // Added: Player's position
    private int playerX;
    private int playerY;

    public Monster(int x, int y) {
        super(x, y);
        this.destructible = true;
        this.visual = "Monster.png";
        this.speed = 1; // Set default speed
        this.direction = Direction.STOP; // Initial direction set to stop
    }

    // Method to update the monster's movement
    public void updateMovement(Player player) {
        // Get the current position of the player
        setPlayerPosition(player.getX(), player.getY());

        // Add logic here to randomly choose the monster's direction of movement
        // Also consider changing direction based on the player's position
        if (random.nextBoolean()) { // Randomly choose to move in a random direction or towards the player
            // Move in a random direction
            Direction[] directions = Direction.values();
            this.direction = directions[random.nextInt(directions.length)];
        } else {
            // Move towards the player
            if (playerX > this.getX()) {
                this.direction = Direction.RIGHT;
            } else if (playerX < this.getX()) {
                this.direction = Direction.LEFT;
            } else if (playerY > this.getY()) {
                this.direction = Direction.DOWN;
            } else if (playerY < this.getY()) {
                this.direction = Direction.UP;
            }
        }
    }

    // Method to process the movement of the monster
    public void move() {
        // Implement movement logic here
        switch (this.direction) {
            case UP:
                this.setY(this.getY() - speed);
                break;
            case DOWN:
                this.setY(this.getY() + speed);
                break;
            case LEFT:
                this.setX(this.getX() - speed);
                break;
            case RIGHT:
                this.setX(this.getX() + speed);
                break;
            default:
                // Do not move
                break;
        }
    }

    // Method to update the player's position (new method)
    private void setPlayerPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }

    // Method to set the direction
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Method to get the direction
    public Direction getDirection() {
        return direction;
    }

    // Method to handle interactions with other tiles
    public void interact(Tile tile) {
        // Implement interaction logic here
        // For example, check if tile is an instance of Player and perform specific actions
        if (tile instanceof Player) {
            // Process interaction with the player
        } else if (tile instanceof Monster) {
            // Process interaction with another monster
        } else if (tile instanceof Brick) {
            // Process interaction with a brick
        }
        // ... Other interactions
    }

    // Method to set the monster's speed
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Method to get the monster's speed
    public int getSpeed() {
        return speed;
    }
}

// Enum to represent directions
enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    STOP // Also add a stop state
}
