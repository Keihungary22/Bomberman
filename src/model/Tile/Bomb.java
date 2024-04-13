package model.Tile;

import model.Game;
import java.util.Timer;
import java.util.TimerTask;

import java.util.EventListener;
import model.BombExplodeListener;

public class Bomb extends Tile {
    private int time = 5;
    private final Player owner;
    private int power;
    private final Timer timer;
    private BombExplodeListener bombExplodeListener;

    public Bomb(int x, int y, Player owner) {
        super(x, y);
        this.destructible = false;
        this.visual = "Bomb.png";
        this.owner = owner;
        this.power = owner.getPower_of_bombs();

        // Create a timer and schedule a task to decrement time every second.
        timer = new Timer();
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
        fireBombExplodeEvent();//Replace Bomb to Empty in the bombsLayer
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