package model.Tile;

import model.Game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.EventListener;
import model.BombExplodeListener;

public class Bomb extends Tile {
    private int time = 5;
    private int exp_time = 1;
    private final Timer timer = new Timer();
    private final Timer exp_timer = new Timer();
    private final Player owner;
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private ArrayList<Bomb> bomb_chain_queue = new ArrayList<>();
    private int power;
    private boolean exploded = false;
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

    //explosions will be disappeared in specific time.(in 2 sec as a default)
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

        //region >> generate row-explosions
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

            //region >> If there exist a bomb at this coordinate, add that bomb into bomb_chain_queue list.
            for (int index = 0; index < Game.bombs.size(); index++){
                if(Game.bombs.get(index).getX() == this.x + i && Game.bombs.get(index).getY() == this.y){
                    if(!bomb_chain_queue.contains(Game.bombs.get(index)) && !Game.bombs.get(index).isExploded()){
                        bomb_chain_queue.add(Game.bombs.get(index));
                        Game.bombs.get(index).setExploded(true);
                        Game.bombs.get(index).timer.cancel();//Cancel the timer of added bomb
                    }
                }
            }
            //endregion
        }
        //endregion

        //region >> generate col-explosions
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

            //region >> If there exist a bomb at this coordinate, add that bomb into bomb_chain_queue list.
            for (int index = 0; index < Game.bombs.size(); index++){
                if(Game.bombs.get(index).getX() == this.x && Game.bombs.get(index).getY() == this.y + i){
                    if(!bomb_chain_queue.contains(Game.bombs.get(index)) && !Game.bombs.get(index).isExploded()){
                        bomb_chain_queue.add(Game.bombs.get(index));
                        Game.bombs.get(index).setExploded(true);
                        Game.bombs.get(index).timer.cancel();//Cancel the timer of added bomb
                    }
                }
            }
            //endregion
        }
        //endregion

        //explosion chain
        //region >> explode bombs those are in the queue.
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        for(Bomb bomb_in_queue : this.bomb_chain_queue){
            scheduler.schedule(bomb_in_queue::explode, 100, TimeUnit.MILLISECONDS);
        }
        //endregion
    }


    private void finishExplosionsEvent(){
        for(Explosion explosion : this.explosions){
            Game.explosions.remove(explosion);
        }

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

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public boolean isExploded() {
        return exploded;
    }
    //endregion


    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Bomb)){
            return false;
        }
        Bomb other = (Bomb) obj;
        return this.x == other.x && this.y == other.y;
    }
}