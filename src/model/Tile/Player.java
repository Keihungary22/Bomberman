package model.Tile;

import model.EventListener.BombExplodeListener;
import model.Game;
import model.EventListener.PlayerDieListener;

public class Player extends Tile implements BombExplodeListener {
    private int score = 0;
    private boolean is_alive = true;
    private int current_number_of_bomb = 0;
    private int max_number_of_bombs = 1;
    private int power_of_bombs = 1;
    private String displayName;
    private PlayerDieListener playerDieListener;
    private int speed = 2;
    private boolean is_invincible_mode = false;
    private boolean is_detonator_mode = false;
    private boolean is_obstacle_mode = false;
    private boolean is_roller_skate_mode = false;
    private boolean is_ghost_mode = false;


    public Player(String visual) {
        super(0, 0);
        this.x = 0;
        this.y = 0;
        this.destructible = true;
        this.visual = visual;
        this.displayName = visual.substring(0, visual.lastIndexOf("."));
    }

    //region >> movement
    private void moveUp(){
        this.y -= 1;
    }
    private void moveDown(){
        this.y += 1;
    }
    private void moveRight(){
        this.x += 1;
    }
    private void moveLeft(){
        this.x -= 1;
    }

    public boolean move(String direction){
        int x = this.x;
        int y = this.y;
        int size = Game.map.getSize();

        int dx = 0;
        int dy = 0;

        switch (direction){
            case "up":
                dy = -1;
                break;
            case "down":
                dy = 1;
                break;
            case "left":
                dx = -1;
                break;
            case "right":
                dx = 1;
                break;
        }

        Tile next_objects_tile = Game.map.getLayers().get("Objects").getTiles().get(size*(y+dy)+x+dx);
        Tile next_bombs_tile = Game.map.getLayers().get("Bombs").getTiles().get(size*(y+dy)+x+dx);
        if(
                !(next_objects_tile instanceof Block)
                &&
                !(next_objects_tile instanceof Brick)
                &&
                !(next_objects_tile instanceof Player)
                &&
                !(next_objects_tile instanceof Box)
                &&
                !(next_bombs_tile instanceof Bomb)
        ){
            if(next_objects_tile instanceof Treasure){
                getTreasure((Treasure) next_objects_tile);
            }
            switch (direction){
                case "up":
                    this.moveUp();
                    break;
                case "down":
                    this.moveDown();
                    break;
                case "left":
                    this.moveLeft();
                    break;
                case "right":
                    this.moveRight();
                    break;
            }

            int new_x = this.x;
            int new_y = this.y;
            if(Game.map.getLayers().get("Bombs").getTiles().get(size*new_y+new_x) instanceof Explosion){
                System.out.println("Move into explosion and die " + this.visual);
                die();
            }
            return true;
        }
        else {
            return false;
        }
    }
    //endregion

    private void getTreasure(Treasure treasure){
        switch (treasure.getVisual()){
            case "item_bomb_power_up.png":
                power_of_bombs++;
                break;
            case "item_bomb_increase.png":
                max_number_of_bombs++;
                break;
            case "item_invincibility.png":
                break;
            case "item_rollerskate.png":
                break;
            case "item_obstacle.png":
                break;
            case "item_ghost.png":
                break;
            case "item_detonator.png":
                break;
        }
        Game.sfxPlayer.play("assets/sound/itemGet.wav");
        Game.treasures.remove(treasure);
    }

    //region >> bomb
    private Bomb generateBomb(){
        return new Bomb(this.x,this.y, this);
    }
    public Bomb putBomb(){
        //if there is not a bomb in the same cell, player can put new bomb
        if(isBombPlaceable()){
            Bomb newBomb = generateBomb();
            Game.bombs.add(newBomb);
            current_number_of_bomb++;
            Game.sfxPlayer.play("assets/sound/placeBomb.wav");
            return newBomb;
        }
        return null;
    }

    private boolean isABombAtMyFeet(){
        return !Game.map.getLayers().get("Bombs").getTiles().get(Game.map.getSize()*this.y + x).getVisual().equals("Empty.png");
    }
    public boolean isBombPlaceable(){
        return !isABombAtMyFeet() && (current_number_of_bomb < max_number_of_bombs);
    }
    //endregion

    void die(){
        is_alive = false;
        firePlayerDieEvent();
    }

    public void setPlayerDieListener(PlayerDieListener listener){
        this.playerDieListener = listener;
    }

    private void firePlayerDieEvent(){
        playerDieListener.playerDie();
    }

    //region >> getter/setter
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void increaseScore(){
        this.score++;
    }

    public boolean isAlive() {
        return is_alive;
    }
    public void setAlive(boolean is_alive) {
        this.is_alive = is_alive;
    }

    public int getMax_number_of_bombs(){
        return max_number_of_bombs;
    }
    public void setMax_number_of_bombs(int max_number_of_bombs){
        this.max_number_of_bombs = max_number_of_bombs;
    }

    public void setPower_of_bombs(int power_of_bombs) {
        this.power_of_bombs = power_of_bombs;
    }
    public int getPower_of_bombs() {
        return power_of_bombs;
    }

    public void setCurrent_number_of_bomb(int current_number_of_bomb){
        this.current_number_of_bomb = current_number_of_bomb;
    }
    public int getCurrent_number_of_bomb() {
        return current_number_of_bomb;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public int getSpeed() {
        return speed;
    }
    public void increaseSpeed(){
        this.speed++;
    }
    public void decreaseSpeed(){
        this.speed--;
    }
    //endregion


    //we need to implement this so that Player can be noticed when bombs explode and can check if he is inside of explosion or not.
    @Override
    public void bombExploded() {
        for(Explosion explosion : Game.explosions){
            if(explosion.getX() == this.x && explosion.getY() == this.y){
                System.out.println("Die " + this.getVisual());
                die();
            }
        }
    }










    //region >> we don't need to implement anything in inside.(we only need to override it for BombExplodeListener)
    @Override
    public void bombFinishExplosion() {

    }

    @Override
    public void bombDestroyedBox() {

    }
    //endregion
}