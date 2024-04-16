package model.Tile;

import model.Game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.util.EventListener;
import model.BombExplodeListener;

public class Bomb extends Tile {
    private int time = 5;
    private int exp_time = 1;
    private final Timer timer = new Timer();
    private final Timer exp_timer = new Timer();
    private final Player owner;
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private int power;
    private BombExplodeListener bombExplodeListener;

    public Bomb(int x, int y, Player owner) {
        super(x, y);
        this.destructible = false;
        this.visual = "Bomb.png";
        this.owner = owner;
        this.power = owner.getPower_of_bombs();

        // Create a timer and schedule a task to decrement time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time--;
                if (time <= 0) {//it the time is over
                    explode();
                    timer.cancel(); // stop timer
                }
            }
        }, 1000, 1000);// Execute the task once 1 second later, and then every second thereafter.
    }

    public void explode(){
        this.owner.setCurrent_number_of_bomb(this.owner.getCurrent_number_of_bomb()-1);//decrease owner-player's number of bomb
        Game.bombs.remove(this);//Remove self from bombs list of Game object
        generateExplosions();
        fireBombExplodeEvent();//GameScreen_GUI class will receive signal and update Bombs layer
        explosionsTimer();//Count down will start. And explosions area will be disappeared when timer is over.
    }

    public void explosionsTimer(){
        exp_timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                exp_time--;
                if (exp_time <= 0) {//it the time is over
                    finishExplosionsEvent();
                    exp_timer.cancel(); // stop timer
                }
            }
        }, 1000, 1000);
    }

    private void generateExplosions(){
        Explosion center = new Explosion(this.x, this.y, "exp_center.png");
        Game.explosions.add(center);
        this.explosions.add(center);
        //region generate row-explosions
        for(int i = -this.power; i <= this.power; i++){
            String visual = "";
            if(i == 0){
                continue; //center explosion was generated already
            }
            else if(i == -this.power){
                visual = "exp_end_left.png";
            }
            else if(i == this.power){
                visual = "exp_end_right.png";
            }
            else{
                visual = "exp_row.png";
            }
            Explosion newExp = new Explosion(this.x + i, this.y, visual);
            Game.explosions.add(newExp);
            this.explosions.add(newExp);
        }
        //endregion

        //region generate col-explosions
        for(int i = -this.power; i <= this.power; i++){
            String visual = "";
            if(i == 0){
                continue; //center explosion was generated already
            }
            else if(i == -this.power){
                visual = "exp_end_top.png";
            }
            else if(i == this.power){
                visual = "exp_end_bottom.png";
            }
            else{
                visual = "exp_col.png";
            }
            Explosion newExp = new Explosion(this.x, this.y + i, visual);
            Game.explosions.add(newExp);
            this.explosions.add(newExp);
        }
        //endregion
    }


    private void finishExplosionsEvent(){
        ArrayList<Explosion> removes = new ArrayList<>();
        for(Explosion game_exp : Game.explosions){
            for(Explosion this_exp : this.explosions){
                if(game_exp.equals(this_exp) && game_exp.getVisual().equals(this_exp.getVisual())){
                    removes.add(this_exp);
                }
            }
        }
        Game.explosions.removeAll(removes); //remove explosions from ArrayList
        if(bombExplodeListener != null){
            bombExplodeListener.bombFinishExplosion();
        }
    }

    private void fireBombExplodeEvent(){
        if(bombExplodeListener != null){
            bombExplodeListener.bombExploded();
        }
    }

    public void setBombExplodeListener(BombExplodeListener listener) {
        this.bombExplodeListener = listener;
    }


    //region >> getter/setter
    public int getTime() {
        return time;
    }
    public void setTime(int time) {}

    public Player getOwner() {
        return owner;
    }
    //endregion
}