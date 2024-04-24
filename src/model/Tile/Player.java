package model.Tile;

import model.EventListener.BombExplodeListener;
import model.EventListener.PlayerStatusChangeListener;
import model.Game;
import model.EventListener.PlayerDieListener;
import model.TreasureType;

import java.util.*;

public class Player extends Tile implements BombExplodeListener {
    private final int id;
    private int score = 0;
    private boolean is_alive = true;
    private int current_number_of_bomb = 0;
    private int max_number_of_bombs = 1;
    private int power_of_bombs = 1;
    private final String displayName;
    private PlayerDieListener playerDieListener;
    private PlayerStatusChangeListener playerStatusChangeListener;
    private int speed = 2;
    private DetonatorBomb currentDetonatorBomb;
    //region >> status for power-ups
    private boolean is_invincible_mode = false;
    private boolean is_detonator_mode = false;
    private boolean is_obstacle_mode = false;
    private boolean is_roller_skate_mode = false;
    private boolean is_ghost_mode = false;
    //endregion
    //region >> power-up times for each power-ups
    private final int power_up_time = 15;
    private int invincible_time = power_up_time;
    private int detonator_time = power_up_time;
    private int obstacle_time = power_up_time;
    private int roller_skate_time = power_up_time;
    private int ghost_time = power_up_time;
    //endregion
    //region >> Timers for each time-limited power-ups
    private Timer invincible_timer = new Timer();
    private Timer detonator_timer = new Timer();
    private Timer obstacle_timer = new Timer();
    private Timer roller_skate_timer = new Timer();
    private Timer ghost_timer = new Timer();
    //endregion


    public Player(String visual, int id) {
        super(0, 0);
        this.id = id;
        this.x = 0;
        this.y = 0;
        this.destructible = true;
        this.visual = visual;
        this.displayName = visual.substring(0, visual.lastIndexOf("."));
    }
    public void die(){
        is_alive = false;
        firePlayerDieEvent();
    }
    public Bomb putBomb(){
        //if there is not a bomb in the same cell, player can put new bomb
        if(isBombPlaceable()){
            Bomb newBomb = generateBomb();
            if(newBomb instanceof DetonatorBomb){
                currentDetonatorBomb = (DetonatorBomb) newBomb;
            }
            Game.bombs.add(newBomb);
            current_number_of_bomb++;
            Game.sfxPlayer.play("assets/sound/placeBomb.wav");
            return newBomb;
        }
        return null;
    }
    public boolean hasDetonatorBomb(){
        return currentDetonatorBomb != null;
    }
    public void explodeDetonatorBomb(){
        currentDetonatorBomb.explode();
        currentDetonatorBomb = null;
    }
    public boolean isBombPlaceable(){
        return !isABombAtMyFeet() && (current_number_of_bomb < max_number_of_bombs);
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

    public boolean move(String direction) throws Exception {
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
                !(next_bombs_tile instanceof NormalBomb)
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

    //region >> status change
    private void getTreasure(Treasure treasure) throws Exception {
        switch (treasure.getType()){
            case BOMB_POWER_UP:
                power_of_bombs++;
                break;
            case BOMB_INCREASE:
                max_number_of_bombs++;
                break;
            case INVINCIBILITY:
                if(!is_invincible_mode){
                    be_invincible();
                }
                break;
            case ROLLERSKATE:
                if(!is_roller_skate_mode){
                    use_roller_skate();
                }
                break;
            case OBSTACLE:
                if(!is_obstacle_mode){
                    be_obstacle_setter();
                }
                break;
            case GHOST:
                if(!is_ghost_mode){
                    be_ghost();
                }
                break;
            case DETONATOR:
                if(!is_detonator_mode){
                    be_detonator_setter();
                }
                break;
        }
        firePlayerStatusChange(this.id, treasure.getType());
        Game.sfxPlayer.play("assets/sound/itemGet.wav");
        Game.treasures.remove(treasure);
    }
    private void be_invincible(){
        is_invincible_mode = true;
        String temp_visual = visual;
        visual = "invincibilityModePlayer.png";
        //region >> Update the timer once canceled to a new instance
        if (invincible_timer != null) {
            invincible_timer.cancel();
            invincible_timer = null;
        }
        invincible_timer = new Timer();
        //endregion
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (invincible_time <= 0) {
                    invincible_timer.cancel();
                    invincible_time = power_up_time;
                    is_invincible_mode = false;
                    visual = temp_visual;
                    System.out.println("invincibility finish");
                    try {
                        firePlayerStatusChangeTimeUp(id, TreasureType.INVINCIBILITY);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    invincible_time--;
                }
            }
        };

        invincible_timer.scheduleAtFixedRate(task, 0, 1000);
    }
    private void be_detonator_setter(){
        is_detonator_mode = true;
        //region >> Update the timer once canceled to a new instance
        if (detonator_timer != null) {
            detonator_timer.cancel();
            detonator_timer = null;
        }
        detonator_timer = new Timer();
        //endregion
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (detonator_time <= 0) {
                    detonator_timer.cancel();
                    detonator_time = power_up_time;
                    is_detonator_mode = false;
                    try {
                        firePlayerStatusChangeTimeUp(id, TreasureType.DETONATOR);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    detonator_time--;
                }
            }
        };

        detonator_timer.scheduleAtFixedRate(task, 0, 1000);
    }
    private void be_obstacle_setter(){
        is_obstacle_mode = true;
        //region >> Update the timer once canceled to a new instance
        if (obstacle_timer != null) {
            obstacle_timer.cancel();
            obstacle_timer = null;
        }
        obstacle_timer = new Timer();
        //endregion
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (obstacle_time <= 0) {
                    obstacle_timer.cancel();
                    obstacle_time = power_up_time;
                    is_obstacle_mode = false;
                    System.out.println("obstacle finish");
                    try {
                        firePlayerStatusChangeTimeUp(id, TreasureType.OBSTACLE);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    obstacle_time--;
                }
            }
        };

        obstacle_timer.scheduleAtFixedRate(task, 0, 1000);
    }
    private void be_ghost(){
        is_ghost_mode = true;
        //region >> Update the timer once canceled to a new instance
        if (ghost_timer != null) {
            ghost_timer.cancel();
            ghost_timer = null;
        }
        ghost_timer = new Timer();
        //endregion
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (ghost_time <= 0) {
                    ghost_timer.cancel();
                    ghost_time = power_up_time;
                    is_ghost_mode = false;
                    try {
                        firePlayerStatusChangeTimeUp(id, TreasureType.GHOST);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    ghost_time--;
                }
            }
        };

        ghost_timer.scheduleAtFixedRate(task, 0, 1000);
    }
    private void use_roller_skate(){
        is_roller_skate_mode = true;
        //region >> Update the timer once canceled to a new instance
        if (roller_skate_timer != null) {
            roller_skate_timer.cancel();
            roller_skate_timer = null;
        }
        roller_skate_timer = new Timer();
        //endregion
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (roller_skate_time <= 0) {
                    roller_skate_time = power_up_time;
                    is_roller_skate_mode = false;
                    try {
                        firePlayerStatusChangeTimeUp(id, TreasureType.ROLLERSKATE);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    roller_skate_time--;
                }
            }
        };

        roller_skate_timer.scheduleAtFixedRate(task, 0, 1000);
    }
    //endregion

    //region >> bomb
    private Bomb generateBomb(){
        if(is_detonator_mode){
            return new DetonatorBomb(this.x, this.y, this);
        }else{
            return new NormalBomb(this.x,this.y, this);
        }
    }
    private boolean isABombAtMyFeet(){
        return !Game.map.getLayers().get("Bombs").getTiles().get(Game.map.getSize()*this.y + x).getVisual().equals("Empty.png");
    }
    //endregion

    //region >> player event listener
    public void setPlayerStatusChangeListener(PlayerStatusChangeListener listener){
        this.playerStatusChangeListener = listener;
    }
    public void setPlayerDieListener(PlayerDieListener listener){
        this.playerDieListener = listener;
    }
    private void firePlayerStatusChange(int player_id, TreasureType treasure_type) throws Exception {
        playerStatusChangeListener.PlayerStatusChanged(player_id, treasure_type);
    }
    private void firePlayerStatusChangeTimeUp(int player_id, TreasureType treasure_type) throws Exception {
        playerStatusChangeListener.PlayerStatusChangedTimeUp(player_id, treasure_type);
    };
    private void firePlayerDieEvent(){
        playerDieListener.playerDie();
    }
    //endregion

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