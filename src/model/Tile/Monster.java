package model.Tile;

import java.util.Random;

public class Monster extends Tile {
    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        STOP // Also add a stop state
    }
    private int speed;
    private Direction direction;
    private final Random random = new Random();
    
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
        int decision = random.nextInt(100);
        if (decision < 20) {  // 20% probability to change direction randomly
            this.direction = Direction.values()[random.nextInt(Direction.values().length)];
        } else if (decision < 40) {  // 20% probability to stop or reconsider strategy
            this.direction = Direction.STOP;
        } else {  // 60% probability to move towards the player
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
            // Assuming Player has a method to handle death
            ((Player)tile).die();
            System.out.println("Player has been killed by a monster.");
        } else if (tile instanceof Monster) {
            // Both monsters change direction to avoid overlap or conflict
            this.setDirection(Direction.values()[random.nextInt(Direction.values().length)]);
            ((Monster)tile).setDirection(Direction.values()[random.nextInt(Direction.values().length)]);
            System.out.println("Two monsters have encountered and changed their directions.");
        }
        else if (tile instanceof Brick) {
            // The monster changes direction upon hitting a brick
            this.setDirection(Direction.values()[random.nextInt(Direction.values().length)]);
            System.out.println("Monster hit a brick and changed direction.");
        }
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
